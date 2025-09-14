package com.JesusCruzGarcia.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static Texture shipSheet;
    public static TextureRegion[][] ships;

    public static Texture enemySheet;
    public static TextureRegion[][] enemies;

    public static Texture miscSheet;

    public static Texture bulletSheet;
    public static TextureRegion[][] bullet;

    public static Texture backgrounds;
    public static TextureRegion[][] background;

    public static void load() {
        // ships.png: 4 columns x 3 rows, each cell is 7x8 pixels
        final int shipCols = 4;
        final int shipRows = 3;
        final int shipCellW = 7;
        final int shipCellH = 8;

        shipSheet = new Texture("ships.png");
        shipSheet.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        int sheetW = shipSheet.getWidth();
        int sheetH = shipSheet.getHeight();
        Gdx.app.log("Assets", "ships.png size: " + sheetW + "x" + sheetH);

        // Validate dimensions; if mismatch, we’ll still attempt to slice, but log a warning
        if (sheetW != shipCols * shipCellW || sheetH != shipRows * shipCellH) {
            Gdx.app.log("Assets", "Warning: expected " + (shipCols * shipCellW) + "x" + (shipRows * shipCellH) +
                    " for a 4x3 grid of 7x8 cells, but got " + sheetW + "x" + sheetH + ". Check the image or cell sizes.");
        }

        // Build grid manually (equivalent to TextureRegion.split with 7x8)
        ships = new TextureRegion[shipRows][shipCols];
        for (int row = 0; row < shipRows; row++) {
            for (int col = 0; col < shipCols; col++) {
                int x = col * shipCellW;
                int y = row * shipCellH;
                ships[row][col] = new TextureRegion(shipSheet, x, y, shipCellW, shipCellH);
            }
        }
        Gdx.app.log("Assets", "ships grid: rows=" + ships.length + ", cols=" + ships[0].length);

        // You can adjust these later
        enemySheet = new Texture("enemies.png");
        enemySheet.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        enemies = new TextureRegion[0][0];

        miscSheet = new Texture("SpaceShooterAssetPack_Miscellaneous.png");
        miscSheet.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        bulletSheet = new Texture("SpaceShooterAssetPack_Projectiles.png");
        bulletSheet.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        bullet = new TextureRegion[0][0];

        backgrounds = new Texture("SpaceShooterAssetPack_BackGrounds.png");
        backgrounds.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        // If using full backgrounds, no split needed
    }

    public static void dispose() {
        if (shipSheet != null) { shipSheet.dispose(); shipSheet = null; }
        if (enemySheet != null) { enemySheet.dispose(); enemySheet = null; }
        if (miscSheet != null) { miscSheet.dispose(); miscSheet = null; }
        if (bulletSheet != null) { bulletSheet.dispose(); bulletSheet = null; }
        if (backgrounds != null) { backgrounds.dispose(); backgrounds = null; }
    }
}
