package com.lilithsthrone.game.dialogue.npcDialogue.dominion;

import com.lilithsthrone.game.dialogue.DialogueFlagValue;
import com.lilithsthrone.game.dialogue.DialogueNodeOld;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.responses.ResponseCombat;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.character.gender.GenderPreference;
import com.lilithsthrone.game.character.npc.dominion.DominionAlleywayAttacker;
import com.lilithsthrone.game.character.npc.dominion.Pixie;
import com.lilithsthrone.main.Main;

public class PixieDialogue {
	public static final DialogueNodeOld PIXIE_INTRO = new DialogueNodeOld("Women running", "", true) {
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
		private Pixie getPixie() {
			return (Pixie)Main.game.getPixie();
		}
	};
	
	public static final DialogueNodeOld PIXIE_FIRST_ENCOUNTER = new DialogueNodeOld("Stranger", "", true) {
		private static final long serialVersionUID = 1L;
		
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
						return PIXIE_COMFORT;
					}
					
					@Override
					public void effects() {
						Main.game.setActiveNPC(new DominionAlleywayAttacker(GenderPreference.getGenderFromUserPreferences()));
						Main.game.getActiveNPC().setLevel(Main.game.getPlayer().getLevel()+1);
					}
				};
				
			} else if(index==2) {
				return new Response("Challenge", "You can't stand such a weak display", null) {
					@Override
					public DialogueNodeOld getNextDialogue() {
						return PIXIE_NOCARE;
					}
					@Override
					public void effects() {
					}
				};
			}
			
			return null;
		}
	};
	
	public static final DialogueNodeOld PIXIE_COMFORT = new DialogueNodeOld("Pixie", "", true) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("characters/dominion/pixie", "PIXIE_FIRST_ENCOUNTER_COMFORT");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new ResponseCombat("Fight", "Defend yourself and you new friend.", Main.game.getActiveNPC());
			} else if(index==2) {
				return new Response("Challenge", "You can't stand such a weak display", null) {
					@Override
					public DialogueNodeOld getNextDialogue() {
						return PIXIE_PLACE_HOLDER;
					}
					@Override
					public void effects() {
					}
				};
			}
			
			return null;
		}
	};
	
	public static final DialogueNodeOld PIXIE_NOCARE = new DialogueNodeOld("Pixie", "", true) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("characters/dominion/pixie", "PIXIE_FIRST_ENCOUNTER_NOCARE");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new ResponseCombat("Fight", "Defend yourself and you new friend.", Main.game.getActiveNPC());
			} else if(index==2) {
				return new Response("Challenge", "You can't stand such a weak display", null) {
					@Override
					public DialogueNodeOld getNextDialogue() {
						return PIXIE_PLACE_HOLDER;
					}
					@Override
					public void effects() {
					}
				};
			}
			
			return null;
		}
	};
	
	public static final DialogueNodeOld PIXIE_PLACE_HOLDER = new DialogueNodeOld("Stranger", "", true) {
		private static final long serialVersionUID = 1L;
		
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
						return Main.game.getDefaultDialogueNoEncounter();
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
