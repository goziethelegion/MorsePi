package code.project.projectcode;

/*
Createed By Andrew McGuire
TEAM PANADA
PROJECT CODE
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;




import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;


public class FragmentHandler extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final UserPreferences prefs = new UserPreferences();
    private int mPage;

    public static FragmentHandler create(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentHandler fragment = new FragmentHandler();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view;

        final ViewPager viewpage = (ViewPager) getActivity().findViewById(R.id.viewpager);

        if(prefs.checkColor().equals("red"))
        {
            viewpage.setBackgroundColor(getResources().getColor(R.color.red));
        }
        if(prefs.checkColor().equals("blue"))
        {
            viewpage.setBackgroundColor(getResources().getColor(R.color.blue));
        }
        if(prefs.checkColor().equals("white"))
        {
            viewpage.setBackgroundColor(getResources().getColor(R.color.white));
        }
        if(prefs.checkColor().equals("green"))
        {
            viewpage.setBackgroundColor(getResources().getColor(R.color.green));
        }



        if(mPage == 1) {
            view = inflater.inflate(R.layout.fragment_morse, container, false);
            Button send = (Button) view.findViewById(R.id.send);
            Button dot = (Button) view.findViewById(R.id.dot);
            Button dash = (Button) view.findViewById(R.id.dash);
            Button space = (Button) view.findViewById(R.id.space);
            Button clear = (Button) view.findViewById(R.id.clear);

            clear.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    TextView message = (TextView) view.findViewById(R.id.message);
                    message.setText("");

                }
            });

            dot.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    TextView message = (TextView) view.findViewById(R.id.message);
                    String msg = message.getText().toString();
                    msg = msg + ".";
                    message.setText(msg);

                    if(prefs.checkSound())
                    {
                        MorseToSound sound = new MorseToSound();
                        sound.codeToSound(".");
                    }
                }
            });

            dash.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    TextView message = ( TextView)view.findViewById(R.id.message);
                    String msg = message.getText().toString();
                    msg = msg + "-";
                    message.setText(msg);

                    if (prefs.checkSound()) {
                        MorseToSound sound = new MorseToSound();
                        sound.codeToSound("-");
                    }
                }
            });

            space.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    TextView message = ( TextView)view.findViewById(R.id.message);
                    String msg = message.getText().toString();
                    msg = msg + " ";
                    message.setText(msg);
                }
            });

            //Selected the send message
            send.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    TextView messageText = ( TextView)view.findViewById(R.id.message);
                    TextView conversation = (TextView) view.findViewById(R.id.translation);
                    String message = messageText.getText().toString();
                    message = MorseToText.fromMorse(message);
                    Log.d("d", message);

                    // now needs to display message
                    conversation.setText(message);
                }
            });

        }

        else if (mPage == 2)  {
            view = inflater.inflate(R.layout.fragment_text, container, false);
            Button send = (Button) view.findViewById(R.id.send);

            //Selected the send message
            send.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {

                    EditText messageText = (EditText)view.findViewById(R.id.message);

                    String message = messageText.getText().toString();
                    if(!message.isEmpty()) {
                        message = TextToMorse.textToMorse(message);
                        // now needs to display message
                        TextView converstaion = (TextView) view.findViewById(R.id.conversation);
                        converstaion.setText(message);

                        if (prefs.checkSound()) {
                            MorseToSound sound = new MorseToSound();
                            sound.morseToSound(message);
                        }
                    }
        }
    });

        }

        else if(mPage == 3) {

            view = inflater.inflate(R.layout.settings, container, false);
            RadioGroup radgroup = (RadioGroup) view.findViewById(R.id.radgroup);

            radgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                    switch (i)
                    {
                        case R.id.blue:
                            prefs.setColor("blue");
                            viewpage.setBackgroundColor(getResources().getColor(R.color.blue));
                            Toast.makeText(ProjectActivity.getAppContext(), "Blue", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.green:
                            prefs.setColor("green");
                            viewpage.setBackgroundColor(getResources().getColor(R.color.green));
                            Toast.makeText(ProjectActivity.getAppContext(), "Green", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.white:
                            prefs.setColor("white");
                            viewpage.setBackgroundColor(getResources().getColor(R.color.white));
                            Toast.makeText(ProjectActivity.getAppContext(), "White", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.red:
                            prefs.setColor("red");
                            viewpage.setBackgroundColor(getResources().getColor(R.color.red));
                            Toast.makeText(ProjectActivity.getAppContext(), "Red", Toast.LENGTH_SHORT).show();
                            break;
                    }


                }
            });


            ToggleButton tog = (ToggleButton) view.findViewById(R.id.soundtog);
            tog.setChecked(prefs.checkSound());
            tog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // The toggle is enabled
                        prefs.setSound(true);

                    } else {
                        // The toggle is disabled
                        prefs.setSound(false);

                    }
                }
            });
        }

        // going to try do access database here.
        else if(mPage == 4)
        {
            view = null;
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
//            onAuthSuccess(mAuth.getCurrentUser());
//
//            view = null;
//            if (!validateForm()) {
//                return null;
//            }

//
//            String email = mEmailField.getText().toString();
//            String password = mPasswordField.getText().toString();

//            mAuth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//
//                            if (task.isSuccessful()) {
//                                task.getResult().getUser();
//                            } else {
//
//                            }
//                        }
//                    });
        }


        else {

            view = inflater.inflate(R.layout.fragment_text,container,false);
        }
        return view;
    }


}