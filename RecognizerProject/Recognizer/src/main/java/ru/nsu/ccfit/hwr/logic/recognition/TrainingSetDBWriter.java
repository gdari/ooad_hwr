package ru.nsu.ccfit.hwr.logic.recognition;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Баира on 22.12.13.
 */
public class TrainingSetDBWriter extends SQLiteOpenHelper  implements TrainingSetWriter {
    public static final String DATABASE_NAME = "patterns.db";	
    private static final int DATABASE_VERSION = 2;
	
    public static final String KEY_LABEL 					= "LABEL";
    public static final String KEY_FEATURE_VECTOR 			= "FEATURE_VECTOR";
    public static final String KEY_TERMINAL_ID	 			= "TERMINAL_ID";
    public static final String TRAINING_SET_TABLE_NAME 		= "TRAINING_SET";
    public static final String TERMINALS_TABLE_NAME		 	= "TERMINALS";

	private static final String TRAINING_SET_TABLE_CREATE 	=
            "CREATE TABLE " + TRAINING_SET_TABLE_NAME + " (" +
                    KEY_TERMINAL_ID + " INTEGER REFERENCES " + 
					TERMINALS_TABLE_NAME + "(" + KEY_TERMINAL_ID + "), " +
                    KEY_FEATURE_VECTOR+ " BLOB NOT NULL);";

    private static final String TERMINALS_TABLE_CREATE =
            "CREATE TABLE " + TERMINALS_TABLE_NAME + " (" + 
			KEY_TERMINAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
			KEY_LABEL + " TEXT UNIQUE NOT NULL);";

    private static final String FIND_LABEL_ID = "SELECT " + KEY_TERMINAL_ID + " FROM "+TERMINALS_TABLE_NAME+" WHERE "+KEY_LABEL+" = ?";

    @Override
    public void writeTrainingSet(TrainingSet resource)throws IOException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Log.i("WRITE", resource.toString());
        for (String label : resource.getLabels() ) {
            Cursor c = null;
            if (db != null) {
                c = db.rawQuery(FIND_LABEL_ID , new String[]{label});
            }
            if (c != null) {
                c.moveToFirst();
            }
            if (c != null) {
                if (c.getCount() == 0) {
                    contentValues.put(KEY_LABEL, label);

                    try {
                        if (contentValues.size() > 0)
                            if (db != null) {
                                db.insert(TERMINALS_TABLE_NAME, null, contentValues);
                            }
                    }catch (SQLiteConstraintException ignored) {}
                }
            }
            c.close();
        }

        if (db != null) {
            db.close();
        }

        db = this.getWritableDatabase();
        for (Terminal t : resource.getTerminals()) {

            Cursor c = null;
            if (db != null) {
                c = db.rawQuery(FIND_LABEL_ID, new String[]{t.getLabel()});
            }
            if (c != null) {
                c.moveToFirst();
            }
            if (c != null) {
                if (c.isAfterLast()) {
                    // err
                } else {
                    Long label_id = c.getLong(0);
                    Log.i(TrainingSetDBWriter.class.getName(), label_id.toString());
                    for (FeatureVector f : t.featureVectorList) {
                        ByteArrayOutputStream bout = new ByteArrayOutputStream();
                        DataOutputStream dout = new DataOutputStream(bout)  ;
                        for (int i = 0; i < f.getSize(); ++i)
                            dout.writeFloat(f.getVector()[i]);
                        byte[] bytes = bout.toByteArray();
                        dout.close();
                        bout.close();
                        ContentValues setValues = new ContentValues();
                        setValues.put(KEY_TERMINAL_ID, label_id);
                        setValues.put(KEY_FEATURE_VECTOR, bytes);
                        try {
                            db.insert(TRAINING_SET_TABLE_NAME, null, setValues);
                        }
                        catch (Exception err) {
                            Log.e(this.getClass().getName(), err.getMessage());
                        }
                    }
                }
            }
            if (c != null) {
                c.close();
            }
        }
        if (db != null) {
            db.close();
        }
        resource.clear();
    }

    public TrainingSetDBWriter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TRAINING_SET_TABLE_CREATE);
        db.execSQL(TERMINALS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //проверяете какая версия сейчас и делаете апдейт
        db.execSQL("DROP TABLE IF EXISTS " + TERMINALS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TRAINING_SET_TABLE_NAME);

        onCreate(db);
    }
}
