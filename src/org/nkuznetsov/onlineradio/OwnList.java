package org.nkuznetsov.onlineradio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class OwnList
{	
	private static final String OWNS_KEY = "owns";
	private static final String OWNS_SEPARATOR = ",";
	
	private static final List<String> owns = new ArrayList<String>();
	private static SharedPreferences preferences;
	
	public static void init(Context context)
	{
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		if (preferences.contains(OWNS_KEY))
			owns.addAll(Arrays.asList(preferences.getString(OWNS_KEY, "").split(OWNS_SEPARATOR)));
	}
	
	private static void save()
	{
		SharedPreferences.Editor editor = preferences.edit();
		if (owns.size() > 0)
			editor.putString(OWNS_KEY, TextUtils.join(OWNS_SEPARATOR, owns));
		else editor.remove(OWNS_KEY);
		editor.commit();
	}
	
	public static boolean isOwn(String id)
	{
		return owns.contains(id);
	}
	
	public static void add(String id)
	{
		if (!isOwn(id)) 
		{
			owns.add(id);
			save();
		}
	}
	
	public static void remove(String id)
	{
		while (isOwn(id)) owns.remove(id);
		save();
	}
}