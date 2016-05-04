package naxess.sqltest2;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editFirstName, editLastName, editGrades, editId;
    Button btnAddData;
    Button btnViewAll;
    Button btnUpdateData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        myDb = new DatabaseHelper(this);
        editFirstName = (EditText)findViewById(R.id.firstname);
        editLastName = (EditText)findViewById(R.id.lastname);
        editGrades = (EditText)findViewById(R.id.grades);
        editId = (EditText)findViewById(R.id.id);
        btnAddData = (Button)findViewById(R.id.button_add);
        btnViewAll = (Button)findViewById(R.id.button_show);
        btnUpdateData = (Button)findViewById(R.id.button_update);
        AddData();
        viewAll();
        updateData();
    }

    public void AddData()
    {
        btnAddData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                boolean isInserted = myDb.insertData(editFirstName.getText().toString(),
                        editLastName.getText().toString(),
                        editGrades.getText().toString());
                if(isInserted == true)
                {
                    Toast.makeText(getApplicationContext(), "Data Inserted Successfully", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void viewAll()
    {
        btnViewAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Cursor res = myDb.getAllData();
                if(res.getCount() == 0)
                {
                    //Toast.makeText(getApplicationContext(), "Data", Toast.LENGTH_LONG).show();
                    showMessage("Error", "No data found.");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext())
                {
                    buffer.append("Id: "+ res.getString(0)+"\n");    //0 = id column, 1 = first name column, etc...
                    buffer.append("First Name: "+res.getString(1)+"\n");
                    buffer.append("Last Name: "+res.getString(2)+"\n");
                    buffer.append("Grades: "+res.getString(3)+"\n\n");
                }
                showMessage("Data",buffer.toString());
            }
        });
    }

    public void updateData()
    {
        btnUpdateData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                boolean isUpdated = myDb.updateData(editId.getText().toString(),
                        editFirstName.getText().toString(),
                        editLastName.getText().toString(),
                        editGrades.getText().toString());
                if(isUpdated == true)
                {
                    Toast.makeText(getApplicationContext(),"Date Updated Successfully", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Date not updated.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
