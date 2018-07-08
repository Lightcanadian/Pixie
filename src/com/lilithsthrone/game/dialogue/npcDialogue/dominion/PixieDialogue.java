package com.lilithsthrone.game.dialogue.npcDialogue.dominion;

import com.lilithsthrone.game.dialogue.DialogueFlagValue;
import com.lilithsthrone.game.dialogue.DialogueNodeOld;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.character.npc.dominion.Pixie;
import com.lilithsthrone.main.Main;

public class PixieDialogue {
	public static final DialogueNodeOld PIXIE_FIRST_MEETING = new DialogueNodeOld("Women running", "", true) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public String getContent() {
			if(Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.pixieFound)) {				
				return UtilText.parseFromXMLFile("characters/dominion/pixie", "PIXIE_INTRO_REPEAT");
			} else if(Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.pixieDisable)){
				return null;			
			} else {
				return UtilText.parseFromXMLFile("characters/dominion/pixie", "PIXIE_INTRO");
			}
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("Investigate ", "Investigate they alley where the woman came from.", null) {
					@Override
					public DialogueNodeOld getNextDialogue() {
						return Main.game.getDefaultDialogueNoEncounter();
					}
					
					@Override
					public void effects() {
						if(Main.game.getPixie() == null) {
							try {
							Main.game.addNPC(new Pixie(), false);							
							((Pixie)Main.game.getPixie()).setRaceConceal(false);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				};
				
			} else if(index==2) {
				return new Response("Get ready", "I'm not ready to fight a monster.", null) {
					@Override
					public DialogueNodeOld getNextDialogue() {
						return Main.game.getDefaultDialogueNoEncounter();
					}
					@Override
					public void effects() {
						Main.game.getDialogueFlags().setFlag(DialogueFlagValue.pixieFound, true);
						if(Main.game.getPixie() == null) {
							try {
							Main.game.addNPC(new Pixie(), false);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				};
			} else if(index==3) {
				return new Response("Leave", "Let the enforcer deal with it.(Will end the questline)", null) {
					@Override
					public DialogueNodeOld getNextDialogue() {
						return Main.game.getDefaultDialogueNoEncounter();
					}
					@Override
					public void effects() {
						Main.game.getDialogueFlags().setFlag(DialogueFlagValue.pixieDisable, true);
						if(Main.game.getPixie() != null) {
							Main.game.removeNPC(Main.game.getPixie());
						}
					}
				};
			}
			
			return null;
		}
	};
}
