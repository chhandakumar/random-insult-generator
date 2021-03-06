package edu.rit.se.agile.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TemplateDAO {
	private SQLiteDatabase database;
	private DatabaseTemplate dbHelper;
	private String[] allColumns = { DatabaseTemplate.COLUMN_ID,
			DatabaseTemplate.COLUMN_CATEGORY,
			DatabaseTemplate.COLUMN_TEMPLATE };


	public TemplateDAO(Context context) {
		dbHelper = new DatabaseTemplate(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		database.close();
	}

	public void populateDatabase() {
		if(!dbHelper.getDatabaseInitialized()) {
			dbHelper.initializeDatabase();
		}
	}

	public Template createTemplate(String value, String category) {
		ContentValues values = new ContentValues();
		values.put(DatabaseTemplate.COLUMN_TEMPLATE, value);
		values.put(DatabaseTemplate.COLUMN_CATEGORY, category);
		long insertId = database.insert(DatabaseTemplate.TABLE_NAME, null,
				values);
		Cursor cursor = database.query(DatabaseTemplate.TABLE_NAME,
				allColumns, null, null,
				null, null, null);
		cursor.moveToFirst();
		Template newTemplete = cursorToTemplate(cursor);
		cursor.close();
		return newTemplete;
	}

	public void deleteTemplate(Template template) {
		long id = template.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(DatabaseTemplate.TABLE_NAME, DatabaseTemplate.COLUMN_ID
				+ " = " + id, null);
	}

	private Template cursorToTemplate(Cursor cursor) {
		Template comment = new Template();
		comment.setId(cursor.getLong(0));
		comment.setCategory(cursor.getString(1));
		comment.setTemplate(cursor.getString(2));
		return comment;
	}

	public List<Template> getAllTemplates() {
		List<Template> comments = new ArrayList<Template>();

		Cursor cursor = database.query(DatabaseTemplate.TABLE_NAME,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Template comment = cursorToTemplate(cursor);
			comments.add(comment);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return comments;
	}
	
	public List<Template> getAllTemplates(String category) {
		List<Template> templates = new ArrayList<Template>();

		Cursor cursor = database.query(DatabaseTemplate.TABLE_NAME,
				allColumns, DatabaseTemplate.COLUMN_CATEGORY + "=?", new String[]{category}, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Template comment = cursorToTemplate(cursor);
			templates.add(comment);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return templates;
	}
}
