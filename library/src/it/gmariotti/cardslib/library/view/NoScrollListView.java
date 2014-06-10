/*
 * ******************************************************************************
 *   Copyright (c) 2013 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */

package it.gmariotti.cardslib.library.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.CardCursorAdapter;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;

public class NoScrollListView extends ListView {

	 private int mPosition;
	 private float originalY;
	 private boolean mbIsScrollEnabled=false;
	 private int mLastAction;
	 private float touchSlop = 0;
	    private float downY = 0;
	    private boolean consumeTouchEvents = false;
	
	public NoScrollListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public NoScrollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public NoScrollListView(Context context, AttributeSet attrs) {
		// TODO Auto-generated constructor stub
		super(context, attrs);
	}
	
 
	 @Override
	    public boolean onTouchEvent(MotionEvent ev) {
	        boolean isHandled = true;
	        switch (ev.getActionMasked()) {
	            case MotionEvent.ACTION_MOVE:
	                float distance = downY - ev.getY();
	                if (!consumeTouchEvents && Math.abs(distance) > touchSlop) {

	                    //send CANCEL event to the AbsListView so it doesn't scroll
	                    ev.setAction(MotionEvent.ACTION_CANCEL);
	                    
	                    consumeTouchEvents = true;
	                    handleScroll(distance);
	                    isHandled = super.onTouchEvent(ev);
	                    handleScroll(distance);
	                     
	                }
	                scrollTo(0, 0);
	                setSelection(0);
	                break;
	            case MotionEvent.ACTION_DOWN:
	                consumeTouchEvents = false;
	                downY = ev.getY();
	                //fallthrough
	            case MotionEvent.ACTION_UP:
	            	setSelection(0);
	                //fallthrough
	            default:
	                if (!consumeTouchEvents) {
	                    isHandled = super.onTouchEvent(ev);
	                    handleScroll(1);
	                }
	                break;

	        }
	        return isHandled;
	    }

	 private void handleScroll(float distance) {
		 scrollTo(0, 0);
	    }
}
