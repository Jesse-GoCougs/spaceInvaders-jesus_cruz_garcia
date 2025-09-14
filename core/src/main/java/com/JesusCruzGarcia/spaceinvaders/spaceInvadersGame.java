package com.JesusCruzGarcia.spaceinvaders;

import com.JesusCruzGarcia.assets.Assets;
import com.JesusCruzGarcia.screens.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// ... existing code ...
public class spaceInvadersGame extends Game {
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        Assets.load();
        setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        // Delegates to the current Screen's render(delta)
        super.render();
    }

    @Override
    public void dispose() {
        if (getScreen() != null) {
            getScreen().dispose();
        }
        if (batch != null) {
            batch.dispose();
            batch = null;
        }
        Assets.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
