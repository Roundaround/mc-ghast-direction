package me.roundaround.ghastdirection.gametest;

import java.util.UUID;

import me.roundaround.allay.api.gametest.ClientGameTest;
import me.roundaround.trove.gametest.ClientTest;
import me.roundaround.trove.gametest.ClientTestContext;
import me.roundaround.trove.gametest.ClientWorld;
import me.roundaround.trove.gametest.GameTestAssertionException;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.phys.Vec3;

/**
 * Vanilla ghasts call {@code faceMovementDirection} every tick via their look goal;
 * with no movement that snaps them to face south (yaw 0). The mod cancels the snap
 * while the ghast is stopped, preserving its heading. Drives the patched method
 * directly on the server ghast — nudge it east, then stop it — and asserts the
 * stopped heading matches the moving one instead of resetting south. Without the
 * mod the stopped read is south and the test fails.
 *
 * <p>Done directly rather than by letting AI tick: the look goal re-faces the ghast
 * every tick and its float-around goal re-engages the instant it is still, leaving
 * only a one-tick window the free-running server can't be stepped into reliably.
 */
@ClientGameTest
public class GhastKeepsHeadingWhenStoppedTest implements ClientTest {
  private static final float SOUTH_YAW = 0.0f;

  @Override
  public void runTest(ClientTestContext context) {
    try (ClientWorld world = context.worldBuilder().creative().stopTime(true).create()) {
      world.teleport(0.5, 65.0, 0.5);
      context.waitTicks(2);

      Ghast ghast = world.summon(EntityTypes.GHAST, new BlockPos(0, 70, 0));
      UUID id = ghast.getUUID();

      float[] movingYaw = { Float.NaN };
      float[] stoppedYaw = { Float.NaN };
      world.runOnServer((server) -> {
        Mob mob = (Mob) requireServerEntity(server, id);
        // No target → faceMovementDirection follows movement (not a shoot target).
        mob.setTarget(null);

        mob.setDeltaMovement(new Vec3(0.5, 0.0, 0.0));
        Ghast.faceMovementDirection(mob);
        movingYaw[0] = mob.getYRot();

        mob.setDeltaMovement(Vec3.ZERO);
        Ghast.faceMovementDirection(mob);
        stoppedYaw[0] = mob.getYRot();
      });

      if (Mth.degreesDifferenceAbs(movingYaw[0], SOUTH_YAW) <= 45.0f) {
        throw new GameTestAssertionException(
            "nudge did not establish a non-south heading (yaw " + movingYaw[0] + ")");
      }
      if (Mth.degreesDifferenceAbs(stoppedYaw[0], movingYaw[0]) > 5.0f) {
        throw new GameTestAssertionException(
            "stopped ghast changed heading (" + movingYaw[0] + " -> " + stoppedYaw[0]
                + ") — mod did not preserve facing; vanilla snaps to south");
      }
    }
  }

  private static Entity requireServerEntity(MinecraftServer server, UUID id) {
    for (ServerLevel level : server.getAllLevels()) {
      Entity entity = level.getEntity(id);
      if (entity != null) {
        return entity;
      }
    }
    throw new GameTestAssertionException("ghast " + id + " not found on the server");
  }
}
