package org.jetbrains.skija.examples.scenes;

import java.util.*;
import java.util.stream.*;
import org.jetbrains.skija.*;
import org.jetbrains.skija.shaper.*;

public class TextLineDecorationsScene extends Scene {
    public TextLineDecorationsScene() {
        _variants = new String[] { "Scaled", "Identity" };
    }

    @Override
    public boolean scale() {
        return "Scaled".equals(variantTitle());
    }

    @Override
    public void draw(Canvas canvas, int width, int height, float dpi, int xpos, int ypos) {
        canvas.translate(20, 40);
        String descenders = " (go-pay)";
        float dx = (width - 40) / 8;

        for (float w = 1f; w <= 10f; ++w) {
            canvas.save();
            float y = 1f + w / 2f;

            try (var line = TextLine.make("Solid " + w + "px" + descenders, inter13);
                 var stroke = new Paint().setColor(0xFFCC3333).setMode(PaintMode.STROKE).setStrokeWidth(w);)
            {
                canvas.drawLine(0, y, (float) Math.floor(line.getWidth()), y, stroke);
                canvas.drawTextLine(line, 0, 0, blackFill);
                canvas.translate(dx, 0);
            }

            try (var line = TextLine.make("Double " + w + "px" + descenders, inter13);
                 var stroke = new Paint().setColor(0xFFCC3333).setMode(PaintMode.STROKE).setStrokeWidth(w);)
            {
                canvas.drawLine(0, y, (float) Math.floor(line.getWidth()), y, stroke);
                canvas.drawLine(0, y + 2 * w, (float) Math.floor(line.getWidth()), y + 2 * w, stroke);
                canvas.drawTextLine(line, 0, 0, blackFill);
                canvas.translate(dx, 0);
            }
            
            try (var line = TextLine.make("Dotted " + w + "px" + descenders, inter13);
                 var circle = new Path().addCircle(w / 2f, 0, w / 2f);
                 var effect = PathEffect.makePath1D(circle, w * 2, 0, PathEffect.Style.TRANSLATE);
                 var stroke = new Paint().setColor(0xFFCC3333).setPathEffect(effect);)
            {
                canvas.drawLine(0, 1f + w / 2f, (float) Math.floor(line.getWidth()) - w, 1f + w / 2f, stroke);
                canvas.drawTextLine(line, 0, 0, blackFill);
                canvas.translate(dx, 0);
            }

            try (var line = TextLine.make("Aligned " + w + "px" + descenders, inter13);) {
                var intervals = (float) Math.floor(line.getWidth() / (w * 2f));
                var interval = line.getWidth() / (intervals + 0.5f);
                try (var circle = new Path().addCircle(w / 2f, 0, w / 2f);
                     var effect = PathEffect.makePath1D(circle, interval, 0, PathEffect.Style.TRANSLATE);
                     var stroke = new Paint().setColor(0xFFCC3333).setPathEffect(effect);)
                {
                    canvas.drawLine(0, 1f + w / 2f, (float) Math.floor(line.getWidth()), 1f + w / 2f, stroke);
                    canvas.drawTextLine(line, 0, 0, blackFill);
                    canvas.translate(dx, 0);
                }
            }

            try (var line = TextLine.make("Dashed " + w + "px" + descenders, inter13);
                 var effect = PathEffect.makeDash(new float[] { w * 3, w * 3 }, 0);
                 var stroke = new Paint().setColor(0xFFCC3333).setMode(PaintMode.STROKE).setStrokeWidth(w).setPathEffect(effect);)
            {
                canvas.drawLine(0, 1f + w / 2f, (float) Math.floor(line.getWidth()), 1f + w / 2f, stroke);
                canvas.drawTextLine(line, 0, 0, blackFill);
                canvas.translate(dx, 0);
            }

            try (var line = TextLine.make("Aligned " + w + "px" + descenders, inter13);) {
                var dashes = (float) Math.floor(line.getWidth() / (w * 6f));
                var dashWidth = line.getWidth() / (dashes + 0.5f) / 2f;

                try (var effect = PathEffect.makeDash(new float[] { dashWidth, dashWidth }, 0);
                     var stroke = new Paint().setColor(0xFFCC3333).setMode(PaintMode.STROKE).setStrokeWidth(w).setPathEffect(effect);)
                {
                    canvas.drawLine(0, 1f + w / 2f, (float) Math.floor(line.getWidth()), 1f + w / 2f, stroke);
                    canvas.drawTextLine(line, 0, 0, blackFill);
                    canvas.translate(dx, 0);
                }
            }

            try (var line = TextLine.make("Wavy " + w + "px" + descenders, inter13);
                 var element = new Path().moveTo(0, -1.5f).lineTo(2, 0.5f).lineTo(4, -1.5f).lineTo(4, -0.5f).lineTo(2, 1.5f).lineTo(0, -0.5f).closePath().transform(Matrix33.makeScale(w));
                 var effect = PathEffect.makePath1D(element, w * 4, 0, PathEffect.Style.TRANSLATE);
                 var stroke = new Paint().setColor(0xFFCC3333).setPathEffect(effect);)
            {
                canvas.save();
                canvas.clipRect(Rect.makeXYWH(0, 1, (float) Math.floor(line.getWidth()), w * 3));
                canvas.drawLine(0, 1f + w * 1.5f, (float) Math.floor(line.getWidth()), 1f + w * 1.5f, stroke);
                canvas.restore();
                canvas.drawTextLine(line, 0, 0, blackFill);
                canvas.translate(dx, 0);
            }

            try (var line = TextLine.make("Striked " + w + "px" + descenders, inter13);
                 var stroke = new Paint().setColor(0xFFCC3333).setMode(PaintMode.STROKE).setStrokeWidth(w);)
            {
                var center = line.getXHeight() * -0.5f;
                center = (float) Math.round(center - w / 2f) + w / 2f;
                canvas.drawLine(0, center, (float) Math.floor(line.getWidth()), center, stroke);
                canvas.drawTextLine(line, 0, 0, blackFill);
                canvas.translate(dx, 0);
            }

            canvas.restore();
            canvas.translate(0, 30);
        }
    }
}