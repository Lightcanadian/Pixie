package com.lilithsthrone.game.character.npc.dominion;
import com.lilithsthrone.game.character.gender.Gender;
import com.lilithsthrone.game.character.persona.Occupation;
import com.lilithsthrone.game.dialogue.DialogueNodeOld;
import com.lilithsthrone.game.dialogue.npcDialogue.dominion.AlleywayAttackerDialogue;
import com.lilithsthrone.game.dialogue.npcDialogue.dominion.AlleywayProstituteDialogue;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.world.places.PlaceType;

/**
 * 
 * @author LightCanadian
 *This is a generic attacker use in story telling to control the encounter dialogue and the end of a combat dialogue and be able to do this format (dialogue > combat > custom dialogue).
 *If none is set, regular dialogue are returned.
 */
public class StorieTellerAttackerNPC extends DominionAlleywayAttacker {
	
	private DialogueNodeOld victoryCombatDialogue;
	private DialogueNodeOld defeatCombatDialogue;
	private DialogueNodeOld victoryProstituteDialogue;
	private DialogueNodeOld defeatProstituteDialogue;
	
	private DialogueNodeOld alleyEncounterProstituteDialogue;
	private DialogueNodeOld alleyEncounterCombatDialogue;
	private DialogueNodeOld alleyEncounterStormAttackDialogue;
	
	public StorieTellerAttackerNPC(Gender gender) {
		super(gender);
	}
	
	@Override
	public Response endCombat(boolean applyEffects, boolean victory) {
		if(this.getHistory()==Occupation.NPC_PROSTITUTE) {
			if (victory) {
				if(victoryProstituteDialogue == null) {
					return new Response("", "", AlleywayProstituteDialogue.AFTER_COMBAT_VICTORY);
				}else {
					return new Response("", "", victoryProstituteDialogue);
				}
			} else {
				if(defeatProstituteDialogue == null) {
					return new Response ("", "", AlleywayProstituteDialogue.AFTER_COMBAT_DEFEAT);
				}else {
					return new Response("", "", defeatProstituteDialogue);
				}
			}
		} else {
			if (victory) {
				if(victoryCombatDialogue == null) {
					return new Response("", "", AlleywayAttackerDialogue.AFTER_COMBAT_VICTORY);
				}else {
					return new Response("", "", victoryCombatDialogue);
				}
			} else {
				if(defeatCombatDialogue == null) {
					return new Response ("", "", AlleywayAttackerDialogue.AFTER_COMBAT_DEFEAT);
				}else {
					return new Response("", "", defeatCombatDialogue);
				}
			}
		}
	}
	
	@Override
	public DialogueNodeOld getEncounterDialogue() {
		PlaceType pt = Main.game.getActiveWorld().getCell(location).getPlace().getPlaceType();
		
		if(pt == PlaceType.DOMINION_BACK_ALLEYS
				|| pt == PlaceType.DOMINION_CANAL
				|| pt == PlaceType.DOMINION_ALLEYS_CANAL_CROSSING
				|| pt == PlaceType.DOMINION_CANAL_END) {
			if(this.getHistory()==Occupation.NPC_PROSTITUTE) {
				this.setPlayerKnowsName(true);
				if(alleyEncounterProstituteDialogue == null) {
					return AlleywayProstituteDialogue.ALLEY_PROSTITUTE;
				}else {
					return alleyEncounterProstituteDialogue;
				}
			} else {
				if(alleyEncounterCombatDialogue == null) {
					return AlleywayAttackerDialogue.ALLEY_ATTACK;
				}else {
					return alleyEncounterCombatDialogue;
				}
			}
			
		} else {
			if(alleyEncounterStormAttackDialogue == null) {
				return AlleywayAttackerDialogue.STORM_ATTACK;
			}else {
				return alleyEncounterStormAttackDialogue;
			}
		}
	}
	
	public void setVictoryCombatDialogue(DialogueNodeOld victoryCombatDialogue) {
		this.victoryCombatDialogue = victoryCombatDialogue;
	}
	
	public void setDefeatCombatDialogue(DialogueNodeOld defeatCombatDialogue) {
		this.defeatCombatDialogue = defeatCombatDialogue;
	}
	
	public void setVictoryProstituteDialogue(DialogueNodeOld victoryProstituteDialogue) {
		this.victoryProstituteDialogue = victoryProstituteDialogue;
	}
	
	public void setDefeatProstituteDialogue(DialogueNodeOld defeatProstituteDialogue) {
		this.defeatProstituteDialogue = defeatProstituteDialogue;
	}
	
	public void setAlleyEncounterProstituteDialogue(DialogueNodeOld alleyEncounterProstituteDialogue) {
	this.alleyEncounterProstituteDialogue = alleyEncounterProstituteDialogue;
	}
	
	public void setAlleyEncounterCombatDialogue(DialogueNodeOld alleyEncounterCombatDialogue) {
		this.alleyEncounterCombatDialogue = alleyEncounterCombatDialogue;
	}
	
	public void setAlleyEncounterStormDialogue(DialogueNodeOld AlleyEncounterStormDialogue) {
		this.alleyEncounterStormAttackDialogue = AlleyEncounterStormDialogue;
	}
}
