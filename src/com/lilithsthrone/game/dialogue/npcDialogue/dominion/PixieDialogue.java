package com.lilithsthrone.game.dialogue.npcDialogue.dominion;

import com.lilithsthrone.game.dialogue.DialogueFlagValue;
import com.lilithsthrone.game.dialogue.DialogueNodeOld;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.responses.ResponseCombat;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.character.gender.GenderPreference;
import com.lilithsthrone.game.character.npc.dominion.Pixie;
import com.lilithsthrone.game.character.npc.dominion.StorieTellerAttackerNPC;
import com.lilithsthrone.main.Main;

public class PixieDialogue {
	public static final DialogueNodeOld PIXIE_INTRO = new DialogueNodeOld("Women running", "", true) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public String getAuthor() {
			return "LightCanadian help by Sasha";
		}
		
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
						return PIXIE_FIRST_ENCOUNTER;
					}
					
					@Override
					public void effects() {
						if(Main.game.getPixie() == null) {
							try {
							Main.game.addNPC(new Pixie(), false);							
							getPixie().setRaceConceal(false);
							} catch (Exception e) {
								e.printStackTrace();
							}
							getPixie().setRaceConceal(true);
						}
						
					}
					
					public Pixie getPixie() {
						return (Pixie)Main.game.getPixie();
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
	
	public static final DialogueNodeOld PIXIE_FIRST_ENCOUNTER = new DialogueNodeOld("Stranger", "", true) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public String getAuthor() {
			return "LightCanadian help by Sasha";
		}
		
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("characters/dominion/pixie", "PIXIE_FIRST_ENCOUNTER");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("Comfort", "Try to comfort this poor soul.", null) {
					@Override
					public DialogueNodeOld getNextDialogue() {
						return PIXIE_ENCOUNTER_COMFORT;
					}
					
					@Override
					public void effects() {
						initEvent();
						Main.game.getDialogueFlags().setFlag(DialogueFlagValue.pixieEncounterComfort, true);
						((StorieTellerAttackerNPC)Main.game.getActiveNPC()).setVictoryCombatDialogue(PIXIE_PLACE_HOLDER);
					}
				};
				
			} else if(index==2) {
				return new Response("Indifferent", "You dont really care about her story.", null) {
					@Override
					public DialogueNodeOld getNextDialogue() {
						return PIXIE_ENCOUNTER_INDIFFERENT;
					}
					@Override
					public void effects() {
						initEvent();
						Main.game.getDialogueFlags().setFlag(DialogueFlagValue.pixieEncounterIndifferent, true);
						((StorieTellerAttackerNPC)Main.game.getActiveNPC()).setVictoryCombatDialogue(PIXIE_PLACE_HOLDER);
					}
				};
			} else if(index==3) {
				return new Response("Manipulate", "You want to manipulate her to add her to your collection", null) {
					@Override
					public DialogueNodeOld getNextDialogue() {
						return PIXIE_ENCOUNTER_MANIPULATE;
					}
					@Override
					public void effects() {
						initEvent();
						Main.game.getDialogueFlags().setFlag(DialogueFlagValue.pixieEncounterManipulate, true);
						((StorieTellerAttackerNPC)Main.game.getActiveNPC()).setVictoryCombatDialogue(PIXIE_PLACE_HOLDER);
					}
				};
			} else if(index==4) {
				return new Response("Egocentric", "She think that she's the only one with issue in this world! But you might get something out of this situation.", null) {
					@Override
					public DialogueNodeOld getNextDialogue() {
						return PIXIE_ENCOUNTER_EGOCENTRIC;
					}
					@Override
					public void effects() {
						initEvent();
						Main.game.getDialogueFlags().setFlag(DialogueFlagValue.pixieEncounterEgocentric, true);
						((StorieTellerAttackerNPC)Main.game.getActiveNPC()).setVictoryCombatDialogue(PIXIE_PLACE_HOLDER);
					}
				};
			} else if(index==5) {
				return new Response("Downright cruel", "YES!!! A new plaything. I was getting horny down there!", null) {
					@Override
					public DialogueNodeOld getNextDialogue() {
						return PIXIE_ENCOUNTER_CRUEL;
					}
					@Override
					public void effects() {						
						initEvent();
						((StorieTellerAttackerNPC)Main.game.getActiveNPC()).setVictoryCombatDialogue(PIXIE_PLACE_HOLDER);
						Main.game.getDialogueFlags().setFlag(DialogueFlagValue.pixieEncounterCruel, true);
					}
				};
			}
			return null;
		}
		
		private void initEvent() {
			createEnemy();
			getPixie().setRaceConceal(false);
			getPixie().setPlayerKnowsName(true);
		}
		
		private void createEnemy() {
			Main.game.setActiveNPC(new StorieTellerAttackerNPC(GenderPreference.getGenderFromUserPreferences()));
			Main.game.getActiveNPC().setLevel(Main.game.getPlayer().getLevel()+1);
			//sometime the NPC doesn't have full health for some reason
			Main.game.getActiveNPC().setHealthPercentage(100);
		}
		
		private Pixie getPixie() {
			return (Pixie)Main.game.getPixie();
		}
		
	};
	
	public static final DialogueNodeOld PIXIE_ENCOUNTER_COMFORT = new DialogueNodeOld("Pixie", "", true) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public String getAuthor() {
			return "LightCanadian help by Sasha";
		}
		
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("characters/dominion/pixie", "PIXIE_FIRST_ENCOUNTER_COMFORT");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new ResponseCombat("Fight", "Defend yourself and you new friend.", Main.game.getActiveNPC());
			} 			
			return null;
		}
	};
	
	public static final DialogueNodeOld PIXIE_ENCOUNTER_INDIFFERENT = new DialogueNodeOld("Pixie", "", true) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public String getAuthor() {
			return "LightCanadian help by Sasha";
		}
		
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("characters/dominion/pixie", "PIXIE_FIRST_ENCOUNTER_INDIFFERENT");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new ResponseCombat("Fight", "Defend yourself and you new friend.", Main.game.getActiveNPC());
			}			
			return null;
		}
	};
	
	public static final DialogueNodeOld PIXIE_ENCOUNTER_MANIPULATE = new DialogueNodeOld("Pixie", "", true) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public String getAuthor() {
			return "LightCanadian help by Sasha";
		}
		
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("characters/dominion/pixie", "PIXIE_FIRST_ENCOUNTER_MANIPULATE");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new ResponseCombat("Fight", "Defend yourself and you new friend.", Main.game.getActiveNPC());
			}			
			return null;
		}
	};
	
	public static final DialogueNodeOld PIXIE_ENCOUNTER_EGOCENTRIC = new DialogueNodeOld("Pixie", "", true) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public String getAuthor() {
			return "LightCanadian help by Sasha";
		}
		
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("characters/dominion/pixie", "PIXIE_FIRST_ENCOUNTER_EGOCENTRIC");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new ResponseCombat("Fight", "Defend yourself and your new property.", Main.game.getActiveNPC());
			}			
			return null;
		}
	};
	
	public static final DialogueNodeOld PIXIE_ENCOUNTER_CRUEL = new DialogueNodeOld("Pixie", "", true) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public String getAuthor() {
			return "LightCanadian help by Sasha";
		}
		
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("characters/dominion/pixie", "PIXIE_FIRST_ENCOUNTER_DOWNRIGHT_CRUEL");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new ResponseCombat("Fight", "Defend yourself and your new property.", Main.game.getActiveNPC());
			}			
			return null;
		}
	};
	
	public static final DialogueNodeOld PIXIE_PLACE_HOLDER = new DialogueNodeOld("Stranger", "", true) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public String getAuthor() {
			return "LightCanadian help by Sasha";
		}
		
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("characters/dominion/pixie", "PLACE_HOLDER");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("Back to game", "No more content is present at the moment", null) {
					@Override
					public DialogueNodeOld getNextDialogue() {
						return AlleywayAttackerDialogue.AFTER_COMBAT_VICTORY;
					}
					
					@Override
					public void effects() {
					}
				};
			}			
			return null;
		}
	};
}
