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
		troll.add("Неудачный денек, да?..");
		troll.add(ChatColor.RED+"Х"+ChatColor.GOLD+"а"+ChatColor.YELLOW+"-"+ChatColor.BLUE+"х"+ChatColor.DARK_BLUE+"а"+ChatColor.DARK_PURPLE+".");
		troll.add("Зато в следующий раз будет не больно!");
		troll.add("После смерти попадаешь не в Вальхаллу, а на спаун.");
		troll.add("Смысл этой жизни был в "+ChatColor.MAGIC+"любви и понимании.");
		troll.add("Смертные так часто умирают, ах.");
		troll.add("Это его боженька наказал!");
		troll.add("Во всем виновата пиратская винда!");
		troll.add("А кишки кто собирать будет?..");
		troll.add("Гуф бы позавидовал - минута боли и ты на спауне.");
		troll.add("Да! Давай еще разок, а чего такого-то.");
		troll.add("Твои неудачи только забавляют Хиробрина.");
		troll.add("Ха-ха, это надо было видеть!");
		troll.add("Мерзость какая, кишки повсюду!");
	}
	
	public String getPlayerLosses() {
		String msg = "";
		int counter = 1;
		for (String txt : losses) {
			
			if (counter == 1) {
				msg += txt; 
			} else if (counter == losses.size()) {
				msg += " и " + txt;
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
