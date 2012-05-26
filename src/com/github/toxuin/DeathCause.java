package com.github.toxuin;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

// THIS CODE IS PART OF DeathControl PLUGIN BY Bone008
// http://dev.bukkit.org/server-mods/deathcontrol/

public enum DeathCause {
	CONTACT("кактуса"),
	SUFFOCATION("удушья"),
	FALL("падения"),
	VOID("падения в бездну"),
	SUICIDE("смертельного суицида"),
	STARVATION("голода"),
	LIGHTNING("молнии"),
	MAGIC("магии"),
	POISON("яда"),
	DROWNING("утопления"),
	LAVA("лавы"),
	FIRE("огня"),
	FIRE_TICK(FIRE, "tick"),
	EXPLOSION("взрыва"),
	MOB("злых монстров"),
	MOB_CREEPER(MOB, "взрыва крипера"),
	MOB_WOLF(MOB, "укуса волка"),
	MOB_ASS(MOB, "плотоядной жопы"),
	PLAYER("руки другого игрока"),
	UNKNOWN("неизвестной болезни");

	private final String name;
	private final String meta;
	public final DeathCause parent;

	private DeathCause(String name){
		this.name = name;
		this.meta = null;
		this.parent = null;
	}
	private DeathCause(DeathCause parent, String meta){
		if(parent == null)
			throw new IllegalArgumentException();
		this.name = parent.name;
		this.meta = meta;
		this.parent = parent;
	}

	public String toName(){
		return this.name;
	}

	/**
	 * Gets a DeathCause parsed from the given parameters.
	 * @param n the main name of the cause
	 * @param m the meta for the cause, or null if there is none
	 * @return the parsed DeathCause, or null if it wasn't found
	 */
	public static DeathCause parseCause(String n, String m){
		String m_ = (m==null ? "" : m);

		for(DeathCause c: values()){
			String c_m = (c.meta==null ? "" : c.meta); 
			if(c.name.equalsIgnoreCase(n) && m_.equalsIgnoreCase(c_m))
				return c;
		}
		return null;
	}

	/**
	 * Retrieves a DeathCause from an EntityDamageEvent.
	 * @param event the event to use
	 * @return the DeathCause that fits the DamageCause of the event
	 */
	public static DeathCause getDeathCause(EntityDamageEvent event){
		if(event != null){
			DamageCause cause = event.getCause();

			if(cause == DamageCause.BLOCK_EXPLOSION || cause == DamageCause.ENTITY_EXPLOSION){
				if(event instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent)event).getDamager() instanceof Creeper)
					return DeathCause.MOB_CREEPER;
				return DeathCause.EXPLOSION;
			}
			if((cause == DamageCause.ENTITY_ATTACK || cause == DamageCause.PROJECTILE) && (event instanceof EntityDamageByEntityEvent)){
				EntityDamageByEntityEvent eEvent = (EntityDamageByEntityEvent)event;
				Entity damager = eEvent.getDamager();
				if(damager instanceof Projectile)
					damager = ((Projectile)damager).getShooter();

				if(damager instanceof Player)
					return DeathCause.PLAYER;
				if(damager instanceof Wolf)
					return DeathCause.MOB_WOLF;
				if(damager instanceof LivingEntity)
					return DeathCause.MOB;
				return DeathCause.UNKNOWN;
			}


			// if no special case matched, check for a direct match
			try{
				DeathCause directMatch = DeathCause.valueOf(cause.toString());
				return directMatch;
			}
			// exception thrown when not found => ignore
			catch(IllegalArgumentException e){}
		}

		// if no valid cause was detected
		return DeathCause.UNKNOWN;
	}


}