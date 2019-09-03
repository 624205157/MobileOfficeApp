package com.mobilepolice.officeMobile.email.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.mobilepolice.officeMobile.email.DButil;


public class AttachmentProvider extends ContentProvider {
	private DButil util;
	@Override
	public boolean onCreate() {
		util=new DButil(getContext());
		return false;
	}
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db=util.getWritableDatabase();
		db.delete("attachment", selection, selectionArgs);
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db=util.getWritableDatabase();
		long id=db.insert("attachment", null, values);
		getContext().getContentResolver().notifyChange(uri, null);
		return 	ContentUris.withAppendedId(uri, id);
	}

	

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db=util.getReadableDatabase();
		Cursor c=db.query("attachment", null, selection, selectionArgs, null, null, null);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
