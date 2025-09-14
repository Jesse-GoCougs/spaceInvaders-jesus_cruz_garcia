package com.JesusCruzGarcia.screens;

import com.JesusCruzGarcia.assets.Assets;
import com.JesusCruzGarcia.spaceinvaders.Player;
import com.JesusCruzGarcia.spaceinvaders.spaceInvadersGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
    private final spaceInvadersGame game;
    private final SpriteBatch batch;

    private OrthographicCamera camera;
    private Viewport viewport;
    private float worldWidth = 800f;   // landscape world size
    private float worldHeight = 480f;  // landscape world size

    private TextureRegion bgRegion;
    private Player player;

    public GameScreen(spaceInvadersGame game) {
        this.game = game;
        this.batch = game.getBatch();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(worldWidth, worldHeight, camera);
        viewport.apply(true);
        camera.position.set(worldWidth * 0.5f, worldHeight * 0.5f, 0f);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Use full background texture for now
        bgRegion = new TextureRegion(Assets.backgrounds);

        // Choose exactly one 7x8 ship from the 3-row x 4-col grid
        // Change these to pick a different ship:
        int row = 0; // 0..2 (top to bottom)
        int col = 0; // 0..3 (left to right)

        TextureRegion playerRegion;
        if (Assets.ships != null && Assets.ships.length > row && Assets.ships[row].length > col) {
            playerRegion = Assets.ships[row][col];
        } else {
            // Fallback: crop top-left 7x8
            Gdx.app.log("GameScreen", "Ships grid unexpected. Falling back to 7x8 at (0,0).");
            playerRegion = new TextureRegion(Assets.shipSheet, 0, 0, 7, 8);
        }

        // Scale up for visibility
        float scale = 4f; // try 3f–6f to taste
        float drawW = playerRegion.getRegionWidth() * scale; // 7 * scale
        float drawH = playerRegion.getRegionHeight() * scale; // 8 * scale

        // Center horizontally using scaled size
        float startX = (worldWidth - drawW) * 0.5f;
        float startY = 16f;

        float speed = worldWidth * 0.6f;

        player = new Player(playerRegion, startX, startY, speed);
        player.setDrawScale(scale); // ensures clamping uses the scaled size
        player.setWorldSize(worldWidth, worldHeight);

        Gdx.app.log("GameScreen", "Using ship row=" + row + ", col=" + col + ", region=" +
                playerRegion.getRegionWidth() + "x" + playerRegion.getRegionHeight() + ", scale=" + scale);
    }

    @Override
    public void render(float delta) {
        player.update(delta);

        Gdx.gl.glClearColor(0.05f, 0.05f, 0.08f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(bgRegion, 0, 0, worldWidth, worldHeight);
        player.render(batch);
        batch.end();
    }

    @Override public void resize(int width, int height) { viewport.update(width, height, true); batch.setProjectionMatrix(camera.combined); }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
    @Override public void dispose() { }
}
