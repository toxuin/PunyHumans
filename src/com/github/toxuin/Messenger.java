package com.github.toxuin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;

public class Messenger {
	public Set<String> losses = new HashSet<String>();
	private final static ArrayList<String> troll = new ArrayList<String>();
	
	static {
		troll.add("��������� �����, ��?..");
		troll.add(ChatColor.RED+"�"+ChatColor.GOLD+"�"+ChatColor.YELLOW+"-"+ChatColor.BLUE+"�"+ChatColor.DARK_BLUE+"�"+ChatColor.DARK_PURPLE+".");
		troll.add("���� � ��������� ��� ����� �� ������!");
		troll.add("����� ������ ��������� �� � ���������, � �� �����.");
		troll.add("����� ���� ����� ��� � "+ChatColor.MAGIC+"����� � ���������.");
		troll.add("�������� ��� ����� �������, ��.");
		troll.add("��� ��� �������� �������!");
		troll.add("�� ���� �������� ��������� �����!");
		troll.add("� ����� ��� �������� �����?..");
		troll.add("��� �� ����������� - ������ ���� � �� �� ������.");
		troll.add("��! ����� ��� �����, � ���� ������-��.");
		troll.add("���� ������� ������ ��������� ���������.");
		troll.add("��-��, ��� ���� ���� ������!");
		troll.add("�������� �����, ����� �������!");
	}
	
	public String getPlayerLosses() {
		String msg = "";
		int counter = 1;
		for (String txt : losses) {
			
			if (counter == 1) {
				msg += txt; 
			} else if (counter == losses.size()) {
				msg += " � " + txt;
			} else {
				msg += ", " + txt;
			}
			counter++;
		}
		return msg;
	}
	
	public static String getTrollPhrase() {
		Collections.shuffle(troll);
		return troll.get(0);
	}
}
