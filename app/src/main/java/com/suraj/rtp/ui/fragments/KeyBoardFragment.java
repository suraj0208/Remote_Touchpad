package com.suraj.rtp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.suraj.rtp.R;
import com.suraj.rtp.ui.CustomEditText;
import com.suraj.rtp.worker.SendMessage;

/**
 * Created by suraj on 1/5/16.
 */

public class KeyBoardFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View keyboardview = inflater.inflate(R.layout.fragment_keyboard, container, false);


        final CustomEditText editText = (CustomEditText) keyboardview.findViewById(R.id.etkeyboard);


        keyboardview.findViewById(R.id.btnctrl).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    new SendMessage("ctrlp").execute();
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    new SendMessage("ctrlr").execute();

                return false;
            }
        });

        keyboardview.findViewById(R.id.btnalt).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    new SendMessage("altp").execute();
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    new SendMessage("altr").execute();

                return false;
            }
        });

        keyboardview.findViewById(R.id.btnsuper).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SendMessage("super").execute();
                    }
                }
        );

        keyboardview.findViewById(R.id.btndel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SendMessage("backspace").execute();
                    }
                }
        );

        keyboardview.findViewById(R.id.btnshutdown).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SendMessage("shutdown").execute();
                    }
                }
        );

        keyboardview.findViewById(R.id.btnreboot).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SendMessage("reboot").execute();
                    }
                }
        );

        keyboardview.findViewById(R.id.btnlock).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SendMessage("lock").execute();
                    }
                }
        );

        keyboardview.findViewById(R.id.btnclose).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SendMessage("close").execute();
                    }
                }
        );

        keyboardview.findViewById(R.id.btndelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendMessage("delete").execute();
            }
        });

        keyboardview.findViewById(R.id.btnenter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendMessage("enter").execute();
            }
        });

        Button functionkeys[] = {
                (Button) keyboardview.findViewById(R.id.btnf1),
                (Button) keyboardview.findViewById(R.id.btnf2),
                (Button) keyboardview.findViewById(R.id.btnf3),
                (Button) keyboardview.findViewById(R.id.btnf4),
                (Button) keyboardview.findViewById(R.id.btnf5),
                (Button) keyboardview.findViewById(R.id.btnf6),
                (Button) keyboardview.findViewById(R.id.btnf7),
                (Button) keyboardview.findViewById(R.id.btnf8)
        };

        Button arrowkeys[] = {
                (Button) keyboardview.findViewById(R.id.btnup),
                (Button) keyboardview.findViewById(R.id.btndown),
                (Button) keyboardview.findViewById(R.id.btnleft),
                (Button) keyboardview.findViewById(R.id.btnright),
        };


        FunctionKeyListener functionKeyListener = new FunctionKeyListener();
        ArrowKeyListener arrowKeyListener = new ArrowKeyListener();

        for (Button btn : functionkeys)
            btn.setOnClickListener(functionKeyListener);

        for (Button btn : arrowkeys)
            btn.setOnClickListener(arrowKeyListener);

        editText.addTextChangedListener(new TextWatcher() {
            int prevlen = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int len = s.length();

                if (prevlen > len) {
                    prevlen = len;
                    return;
                }

                prevlen = len;
                if (len != 0)
                    new SendMessage(Character.toString(s.charAt(s.length() - 1))).execute();

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        return keyboardview;
    }
}

class FunctionKeyListener implements View.OnClickListener {


    @Override
    public void onClick(View v) {
        new SendMessage(((Button) v).getText().toString()).execute();
    }
}

class ArrowKeyListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        new SendMessage(((Button) v).getText().toString().toLowerCase() + "arrow").execute();

    }
}



