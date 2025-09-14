package com.JesusCruzGarcia.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private final TextureRegion region;   // Sub-image of a spritesheet or full image
    private float x;
    private float y;
    private final float speed;            // units per second
    private final Rectangle bounds;       // for collisions later
    private float worldWidth;             // logical world size for clamping
    private float worldHeight;            // logical world size for clamping

    // draw size (allows scaling independently of source region)
    private float drawWidth;
    private float drawHeight;

    public Player(TextureRegion region, float startX, float startY, float speed) {
        this.region = region;
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.drawWidth = region.getRegionWidth();
        this.drawHeight = region.getRegionHeight();
        this.bounds = new Rectangle(startX, startY, drawWidth, drawHeight);
        // Default to current screen size if world size not set explicitly
        this.worldWidth = Gdx.graphics.getWidth();
        this.worldHeight = Gdx.graphics.getHeight();
    }

    public void setDrawScale(float scale) {
        this.drawWidth = region.getRegionWidth() * scale;
        this.drawHeight = region.getRegionHeight() * scale;
        // update bounds to the new size
        this.bounds.setSize(drawWidth, drawHeight);
    }

    public void update(float delta) {
        float dx = 0f;
        float dy = 0f;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) dx -= speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) dx += speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) dy += speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) dy -= speed * delta;

        x += dx;
        y += dy;

        // Clamp to world size using the DRAWN size (so scaling is respected)
        float maxX = worldWidth - drawWidth;
        float maxY = worldHeight - drawHeight;
        x = MathUtils.clamp(x, 0, Math.max(0, maxX));
        y = MathUtils.clamp(y, 0, Math.max(0, maxY));

        bounds.setPosition(x, y);
    }

    public void render(SpriteBatch batch) {
        // Draw scaled
        batch.draw(region, x, y, drawWidth, drawHeight);
    }

    public Rectangle getBounds() { return bounds; }
    public float getX() { return x; }
    public float getY() { return y; }

    public void setWorldSize(float worldWidth, float worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }
}
