package edu.rit.se.agile.randominsultapp;

import java.util.List;

import edu.rit.se.agile.data.Template;
import edu.rit.se.agile.data.TemplateDAO;
import edu.rit.se.agile.data.WordDAO;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class RandomInsults extends Activity {

	private Button generateButton;
	private TextView insultTextField;
	private TemplateDAO templateDAO;
	private WordDAO wordDAO;
	private Spinner categorySpinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_random_insults);
		
		insultTextField = (TextView) findViewById( R.id.insult_display );
		generateButton = (Button) findViewById(R.id.button_generate);
		categorySpinner = (Spinner) findViewById(R.id.category_spinner);

		templateDAO = new TemplateDAO(this);
		wordDAO  = new WordDAO(this);

		templateDAO.open();
		wordDAO.open();
		

		// TODO: Add a cursor adapter from DAO once categories are implemented
		//TODO: zach look at dis
		//categorySpinner.setAdapter(new SimpleCursorAdapter(getApplicationContext(), 0, wordDAO.getCategories(), null, null));
		
		generateButton.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				List<Template> temp = templateDAO.getAllTemplates();
				insultTextField.setText("Some insult.");
				if(temp.size() > 0 ) {
					insultTextField.setText(temp.get(0).getTemplate());
				}
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.random_insults, menu);
		return true;
	}

}