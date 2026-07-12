/*
 * Copyright (c) 2014 Benjamin Schieder
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.anderdonau.spacetrader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

import de.anderdonau.spacetrader.DataTypes.Ship;
import de.anderdonau.spacetrader.DataTypes.ShipTypes;

@SuppressWarnings("UnusedDeclaration")
public class RenderShip extends View {
	protected final Paint     paint     = new Paint();
	private         GameState gameState = null;
	private Ship mShip;

	private boolean rotate = false;
	Bitmap bitmap;
	Bitmap bitmap_shield;
	Bitmap bitmap_damaged;
	Rect src = new Rect();
	Rect dst = new Rect();
	private int fleetSize = 1;

	public RenderShip(Context context) {
		super(context);
	}

	public RenderShip(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RenderShip(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	private Bitmap rotateBitmap(Bitmap src) {
		Matrix m = new Matrix();
		m.preScale(-1, 1);
		return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
	}

	public void setRotate(boolean rotate) {
		if (rotate != this.rotate) {
			bitmap = rotateBitmap(bitmap);
			bitmap_shield = rotateBitmap(bitmap_shield);
			bitmap_damaged = rotateBitmap(bitmap_damaged);
			this.rotate = rotate;
		}
	}

	public void setFleetSize(int fleetSize) {
		this.fleetSize = Math.max(1, Math.min(GameState.MAXENCOUNTERFLEETSIZE, fleetSize));
		invalidate();
	}

	public void setShip(Ship ship) {
		this.mShip = ship;
		this.rotate = false;
		if (gameState == null) {
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.beetle);
			bitmap_shield = BitmapFactory.decodeResource(getResources(), R.drawable.beetle_shield);
			bitmap_damaged = BitmapFactory.decodeResource(getResources(), R.drawable.beetle_damaged);
		} else {
			bitmap = BitmapFactory.decodeResource(getResources(), mShip.getType().drawable);
			bitmap_damaged = BitmapFactory.decodeResource(getResources(),
			                                              mShip.getType().drawable_damaged
			);
			bitmap_shield = BitmapFactory.decodeResource(getResources(), mShip.getType().drawable_shield);
		}
	}

	private int clampPercent(int value) {
		return Math.max(0, Math.min(100, value));
	}

	private float bitmapScale(Bitmap image, int width, int height) {
		if (image == null || image.getWidth() <= 0 || image.getHeight() <= 0 || width <= 0 || height <= 0) {
			return 1.0f;
		}
		return Math.min(1.0f, Math.min(width / (float) image.getWidth(), height / (float) image.getHeight()));
	}

	private void setCenteredDst(Bitmap image, int left, int top, int width, int height) {
		float scale = bitmapScale(image, width, height);
		int drawWidth = Math.max(1, Math.round(image.getWidth() * scale));
		int drawHeight = Math.max(1, Math.round(image.getHeight() * scale));
		dst.left = left + width / 2 - drawWidth / 2;
		dst.top = top + height / 2 - drawHeight / 2;
		dst.right = dst.left + drawWidth;
		dst.bottom = dst.top + drawHeight;
	}

	private void drawShipArt(Canvas canvas, int dmgPercent, int shieldPercent, int left, int top, int width, int height) {
		dmgPercent = clampPercent(dmgPercent);
		shieldPercent = clampPercent(shieldPercent);

		if (shieldPercent > 0) {
			src.top = 0;
			src.bottom = bitmap_shield.getHeight();
			src.left = 0;
			src.right = bitmap_shield.getWidth();
			setCenteredDst(bitmap_shield, left, top, width, height);
			if (this.rotate) {
				int visibleWidth = (int) Math.floor((dst.right - dst.left) * shieldPercent / 100.0);
				canvas.save();
				canvas.clipRect(dst.right - visibleWidth, dst.top, dst.right, dst.bottom);
				canvas.drawBitmap(bitmap_shield, src, dst, paint);
				canvas.restore();
			} else {
				int fullSrcRight = src.right;
				int fullDstRight = dst.right;
				src.right = bitmap_shield.getWidth() * shieldPercent / 100;
				dst.right = dst.left + (fullDstRight - dst.left) * shieldPercent / 100;
				canvas.drawBitmap(bitmap_shield, src, dst, paint);
				src.right = fullSrcRight;
				dst.right = fullDstRight;
			}
		}

		src.top = 0;
		src.bottom = bitmap_damaged.getHeight();
		src.left = 0;
		src.right = bitmap_damaged.getWidth();
		setCenteredDst(bitmap_damaged, left, top, width, height);
		canvas.drawBitmap(bitmap_damaged, src, dst, paint);

		src.top = 0;
		src.bottom = bitmap.getHeight();
		src.left = 0;
		src.right = bitmap.getWidth();
		setCenteredDst(bitmap, left, top, width, height);
		if (this.rotate) {
			int visibleWidth = (int) Math.floor((dst.right - dst.left) * dmgPercent / 100.0);
			canvas.save();
			canvas.clipRect(dst.right - visibleWidth, dst.top, dst.right, dst.bottom);
			canvas.drawBitmap(bitmap, src, dst, paint);
			canvas.restore();
		} else {
			int fullSrcRight = src.right;
			int fullDstRight = dst.right;
			src.right = bitmap.getWidth() * dmgPercent / 100;
			dst.right = dst.left + (fullDstRight - dst.left) * dmgPercent / 100;
			canvas.drawBitmap(bitmap, src, dst, paint);
			src.right = fullSrcRight;
			dst.right = fullDstRight;
		}
	}

	private void drawFormation(Canvas canvas, int dmgPercent, int shieldPercent) {
		int count = Math.max(1, Math.min(GameState.MAXENCOUNTERFLEETSIZE, fleetSize));
		if (count <= 1) {
			drawShipArt(canvas, dmgPercent, shieldPercent, 0, 0, getWidth(), getHeight());
			return;
		}

		float[] xs = new float[]{0.34f, 0.57f, 0.43f, 0.68f, 0.25f};
		float[] ys = new float[]{0.36f, 0.58f, 0.72f, 0.30f, 0.66f};
		if (count == 2) {
			xs = new float[]{0.38f, 0.60f};
			ys = new float[]{0.40f, 0.62f};
		} else if (count == 3) {
			xs = new float[]{0.35f, 0.61f, 0.47f};
			ys = new float[]{0.42f, 0.39f, 0.70f};
		}
		int cell = Math.max(1, Math.round(Math.min(getWidth(), getHeight()) * (count >= 4 ? 0.46f : count == 3 ? 0.52f : 0.58f)));
		for (int i = 0; i < count; i++) {
			float x = this.rotate ? 1.0f - xs[i] : xs[i];
			int left = Math.round(getWidth() * x) - cell / 2;
			int top = Math.round(getHeight() * ys[i]) - cell / 2;
			left = Math.max(0, Math.min(getWidth() - cell, left));
			top = Math.max(0, Math.min(getHeight() - cell, top));
			drawShipArt(canvas, dmgPercent, shieldPercent, left, top, cell, cell);
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		int dmgPercent;
		int shieldPercent;
		if (mShip == null) {
			Random rand = new Random();
			int[] cargo = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			int[] weapon = {1, -1, -1};
			int[] shield = {1, -1, -1};
			int[] shieldStrength = {rand.nextInt(GameState.ESHIELDPOWER), -1, -1};
			int[] gadget = {-1, -1, -1};
			int[] crew = {1, -1, -1};
			int fuel = 10;
			int hull = rand.nextInt(200);
			int tribbles = 0;
			Ship s = new Ship(ShipTypes.BEETLE, cargo, weapon, shield, shieldStrength, gadget, crew, fuel,
				hull, tribbles, gameState);
			this.setShip(s);
			this.setRotate(this.getId() == R.id.EncounterPlayerOpponent);
		}

		if (gameState == null) {
			dmgPercent = (mShip.hull * 100) / 200;
			shieldPercent = (mShip.shieldStrength[0] * 100 / GameState.ESHIELDPOWER);
		} else {
			if (mShip.TotalShields() > 0) {
				shieldPercent = (mShip.TotalShieldStrength() * 100) / mShip.TotalShields();
			} else {
				shieldPercent = -1;
			}
			dmgPercent = (mShip.hull * 100) / mShip.GetHullStrength();
		}

		drawFormation(canvas, dmgPercent, shieldPercent);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int size;
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
		int heigthWithoutPadding = height - getPaddingTop() - getPaddingBottom();

		// set the dimensions
		if (widthWithoutPadding > heigthWithoutPadding) {
			size = heigthWithoutPadding;
		} else {
			size = widthWithoutPadding;
		}

		setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(),
		                     size + getPaddingTop() + getPaddingBottom()
		);
	}
}
