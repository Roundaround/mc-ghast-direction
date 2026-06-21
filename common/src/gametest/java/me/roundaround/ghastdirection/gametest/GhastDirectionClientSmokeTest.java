package me.roundaround.ghastdirection.gametest;

import me.roundaround.allay.api.gametest.ClientGameTest;
import me.roundaround.trove.gametest.ClientTest;
import me.roundaround.trove.gametest.ClientTestContext;
import me.roundaround.trove.gametest.ClientWorld;
import me.roundaround.trove.gametest.GameTestAssertionException;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.Ghast;

/**
 * Summons a ghast and lets it tick/render: the mod's {@code faceMovementDirection}
 * mixin runs each tick, so an injection crash kills the client and fails the test.
 * Exact facing isn't asserted — only that a live ghast survives the mixin path.
 */
@ClientGameTest
public class GhastDirectionClientSmokeTest implements ClientTest {
  @Override
  public void runTest(ClientTestContext context) {
    try (ClientWorld world = context.worldBuilder().creative().stopTime(true).create()) {
      world.teleport(0.5, 65.0, 0.5);
      context.waitTicks(2);

      Ghast ghast = world.summon(EntityTypes.GHAST, new BlockPos(0, 70, 0));
      context.waitTicks(10);

      if (!ghast.isAlive()) {
        throw new GameTestAssertionException("ghast did not survive the mod's mixin tick path");
      }
    }
  }
}
