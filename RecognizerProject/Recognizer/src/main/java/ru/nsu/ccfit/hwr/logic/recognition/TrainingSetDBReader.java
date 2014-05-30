package ru.nsu.ccfit.hwr.logic.recognition;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * Created by Баира on 22.12.13.
 */
public class TrainingSetDBReader extends SQLiteOpenHelper implements TrainingSetReader {

    private static final int DATABASE_VERSION = 2;
    private static final String labelPseudo = "LABEL_STR";
    private static final String fvPseudo = "FEATURE_VECTOR";
    private static final String FULL_TABLE_QUERY =
            "SELECT " 	+ TrainingSetDBWriter.TERMINALS_TABLE_NAME 	+"."+TrainingSetDBWriter.KEY_LABEL + " as "+ labelPseudo + ", " +
						  TrainingSetDBWriter.TRAINING_SET_TABLE_NAME +"."+TrainingSetDBWriter.KEY_FEATURE_VECTOR+ " as " + fvPseudo +
            " FROM "  	+ TrainingSetDBWriter.TRAINING_SET_TABLE_NAME + " JOIN " + TrainingSetDBWriter.TERMINALS_TABLE_NAME +
            " ON " 		+ TrainingSetDBWriter.TRAINING_SET_TABLE_NAME +"." + TrainingSetDBWriter.KEY_TERMINAL_ID + "=" +
						  TrainingSetDBWriter.TERMINALS_TABLE_NAME +"." + TrainingSetDBWriter.KEY_TERMINAL_ID;

    public TrainingSetDBReader(Context context) {
        super(context, TrainingSetDBWriter.DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public TrainingSet readTrainingSet( ) {
        TrainingSet trainingSet = new TrainingSet();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = null;
        if (db != null) {
            c = db.rawQuery(FULL_TABLE_QUERY, null);
        }
        int lIdx = 0;
        if (c != null) {
            lIdx = c.getColumnIndex(labelPseudo);
        }
        int fvIdx = 0;
        if (c != null) {
            fvIdx = c.getColumnIndex(fvPseudo);
        }
        c.moveToFirst();
        do {
            byte[] b = c.getBlob(fvIdx);
            String l = c.getString(lIdx);
            FloatBuffer floatBuffer= ByteBuffer.wrap(b).asFloatBuffer();

            final float[] dst = new float[floatBuffer.capacity()];
            floatBuffer.get(dst);
            trainingSet.addPattern(l, new FeatureVector(dst));
        }
        while (c.moveToNext());

        c.close();
        if (db != null) {
            db.close();
        }
        return trainingSet;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
