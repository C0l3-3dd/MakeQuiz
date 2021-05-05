package com.example.makequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText question ;
    private EditText answer;
    private TextView list;
    private ArrayList<String> array_question = new ArrayList<String>();
    private ArrayList<String> array_answer = new ArrayList<String>();
    int cont=0;

    RequestQueue requestQueue;
    private static final String URL1 ="https://teamtntfcc.sytes.net/quiz/save.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        question = (EditText)findViewById(R.id.question_textbox);
        answer = (EditText)findViewById(R.id.answer_texbox);
        list =(TextView)findViewById(R.id.list);
        array_question.clear();
        array_answer.clear();
        question.setText("");
        answer.setText("");
        question.setFocusable(true);
        list.setText("");
        requestQueue = Volley.newRequestQueue(this);

    }

    public void listAdd(View view)
    {
       // Toast.makeText(this,question.getText(),Toast.LENGTH_SHORT).show();
        if(question.getText().toString().isEmpty() || answer.getText().toString().isEmpty() )
        {
            Toast.makeText(this,"No puede haber campos vacios",Toast.LENGTH_SHORT).show();
        }else
        {
            if(array_question.size() > 0)
            {
                for(int i = 0 ; i < array_question.size();i++)
                {
                    if(question.getText().toString().equals(array_question.get(i)))
                    {
                        Toast.makeText(this,"La pregunta ya exite...",Toast.LENGTH_SHORT).show();
                    }else
                    {
                        array_question.add(question.getText().toString().trim());
                        array_answer.add(answer.getText().toString().trim());
                        cont++;
                        list.setText(list.getText().toString()+"\n"+cont+"-> Question: ¿"+question.getText().toString()+"? | -> Answer: "+answer.getText().toString());
                        question.setText("");
                        answer.setText("");
                        break;
                    }
                }
                question.requestFocus();
            }
            else
            {
                array_question.add(question.getText().toString().trim());
                array_answer.add(answer.getText().toString().trim());
                cont++;
                list.setText(cont+"-> Question: ¿"+question.getText().toString()+"? | -> Answer: "+answer.getText().toString());
                question.setText("");
                answer.setText("");
                question.requestFocus();
            }
        }
    }
    public void saveDB(View view)
    {
        for(int i = 0 ; i < array_question.size(); i++)
        {
           addquiz(String.valueOf(array_question.get(i)),String.valueOf(array_answer.get(i)));
        }
    }
    private void addquiz(final String question, final String answer )
    {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this,"Correcto",Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<>();
               params.put("question",question);
               params.put("answer", answer);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}