package com.example.SaberiGhdamShomar;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class BlinkingTextView extends androidx.appcompat.widget.AppCompatTextView {


    public BlinkingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void animateBlink(final boolean red) {
        if (animator != null) {
            animator.drop();
        }
        animator = new Animator(this, red);
        animator.start();
    }

    public void clearBlinkAnimation() {
        if (animator != null) {
            animator.drop();
        }
    }

    private Animator animator;

    private final static class Animator extends Thread {
        public Animator(final TextView textView, final boolean red) {
            this.textView = textView;
            if (red) {
                SET_TO_USE = RED;
            } else {
                SET_TO_USE = GREEN;
            }
        }

        private TextView textView;

        private final int[] SET_TO_USE;

        private final static int[] RED = {
                -2142396,
                -2008754,
                -1874854,
                -1740697,
                -1540490,
                -1405563,
                -1205099,
                -1004634,
                -804170,
                -669243,
                -469036,
                -334879,
                -200979,
                -67337,
                -1
        };
        private final static int[] GREEN = {
                -6959821,
                -6565826,
                -6106293,
                -5646758,
                -5055894,
                -4530309,
                -3939444,
                -3283042,
                -2692177,
                -2166592,
                -1575728,
                -1116193,
                -656660,
                -262665,
                -1
        };

        private boolean stop;

        @Override
        public void run() {
            int i = 0;
            while (i < 15) {
                if (stop) break;
                final int color = SET_TO_USE[i];
                if (stop) break;
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!stop) {
                            textView.setTextColor(color);
                        }
                    }
                });
                if (stop) break;
                i++;
                if (stop) break;
                try {
                    Thread.sleep(66);
                } catch (InterruptedException e) {}
                if (stop) break;
            }
        }

        public void drop() {
            stop = true;
        }
    }


}
