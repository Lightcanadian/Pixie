package com.lilithsthrone.game.character.npc.dominion;



import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.lilithsthrone.game.character.CharacterImportSetting;
import com.lilithsthrone.game.character.body.Covering;
import com.lilithsthrone.game.character.body.types.BodyCoveringType;
import com.lilithsthrone.game.character.body.types.BreastType;
import com.lilithsthrone.game.character.body.types.EyeType;
import com.lilithsthrone.game.character.body.types.HornType;
import com.lilithsthrone.game.character.body.types.VaginaType;
import com.lilithsthrone.game.character.body.valueEnums.BodySize;
import com.lilithsthrone.game.character.body.valueEnums.Capacity;
import com.lilithsthrone.game.character.body.valueEnums.CupSize;
import com.lilithsthrone.game.character.body.valueEnums.EyeShape;
import com.lilithsthrone.game.character.body.valueEnums.HairStyle;
import com.lilithsthrone.game.character.body.valueEnums.Muscle;
import com.lilithsthrone.game.character.gender.Gender;
import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.character.persona.NameTriplet;
import com.lilithsthrone.game.character.persona.PersonalityTrait;
import com.lilithsthrone.game.character.persona.PersonalityWeight;
import com.lilithsthrone.game.character.persona.SexualOrientation;
import com.lilithsthrone.game.character.race.RaceStage;
import com.lilithsthrone.game.character.race.RacialBody;
import com.lilithsthrone.game.dialogue.DialogueNodeOld;
import com.lilithsthrone.game.dialogue.npcDialogue.dominion.PixieDialogue;
import com.lilithsthrone.game.inventory.CharacterInventory;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Colour;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Util.Value;
import com.lilithsthrone.world.WorldType;
import com.lilithsthrone.world.places.PlaceType;



public class Pixie extends NPC {
	private static final long serialVersionUID = 1L;	
	
	private boolean concealed = true;
	
	public Pixie() {
		this(false);
	}
	
	public Pixie(boolean isImported){
		super(new NameTriplet("Pixel","Pixie","Pixie"), "A young succubi that you found in an alley after she was abandon by her mother.",
				0, Main.game.getDateNow().getMonth(), Main.game.getDateNow().getDayOfMonth(), 
				1, Gender.F_P_V_B_FUTANARI, RacialBody.HUMAN, RaceStage.PARTIAL,
				new CharacterInventory(0), WorldType.DOMINION, PlaceType.DOMINION_BACK_ALLEYS, true);
		
		this.setPersonality(Util.newHashMapOfValues(
				new Value<>(PersonalityTrait.AGREEABLENESS, PersonalityWeight.HIGH),
				new Value<>(PersonalityTrait.CONSCIENTIOUSNESS, PersonalityWeight.LOW),
				new Value<>(PersonalityTrait.EXTROVERSION, PersonalityWeight.LOW),
				new Value<>(PersonalityTrait.NEUROTICISM, PersonalityWeight.HIGH),
				new Value<>(PersonalityTrait.ADVENTUROUSNESS, PersonalityWeight.AVERAGE)));
		
		if(!isImported) {			
			//-----CORE-----/
			this.setHeight(152);//Short(about 5 feet)
			this.setFemininity(60);//low feminity at the beginning
			this.setSexualOrientation(SexualOrientation.AMBIPHILIC);
			
			//-----EYE-----//
			this.setEyeCovering(new Covering(BodyCoveringType.EYE_DEMON_COMMON, Colour.EYE_AQUA));
			this.setEyeType(EyeType.DEMON_COMMON);
			this.setIrisShape(EyeShape.VERTICAL);
			
			//-----SKIN-----//
			this.setSkinCovering(new Covering(BodyCoveringType.HUMAN,Colour.SKIN_LIGHT ), true);
			this.setSkinCovering(new Covering(BodyCoveringType.DEMON_COMMON, Colour.SKIN_RED), true);							
			
			//-----HAIRE-----//
			this.setHairCovering(new Covering(BodyCoveringType.HAIR_HUMAN, Colour.COVERING_BLACK), true);
			this.setHairStyle(HairStyle.MESSY);
			
			//-----VAGINA-----//
			this.setVaginaVirgin(true);
			this.setVaginaType(VaginaType.HUMAN);
			this.setVaginaCapacity(Capacity.TWO_TIGHT.getMedianValue(), true);
			
			//-----BREAST-----//
			this.setBreastType(BreastType.HUMAN);
			this.setBreastSize(CupSize.A.getMeasurement());
			this.setBreastRows(1);
			
			//-----BODY SHAPE-----//
			this.setMuscle(Muscle.ZERO_SOFT.getMedianValue());
			this.setBodySize(BodySize.ZERO_SKINNY.getMedianValue());//Give gaunt bodyshape
			
			//-----HORN-----//
			this.setHornLength(2);
			this.setHornRows(1);
			this.setHornType(HornType.SWEPT_BACK);			
			
			//-----PENIS-----//
			this.setPenisCumStorage(1);
			this.setPenisCumProductionRegeneration(5);
			this.setPenisVirgin(true);
			this.setPenisSize(3);
			
			//-----TESTICULES-----//
			this.setInternalTesticles(true);
			
			
			this.setPlayerKnowsName(false);
			//-----LOCATION-----//
			//this.setLocation(Main.game.getPlayer().getLocation());//For debuggin she spawn in our room at the beginning
		}

	}
	
	@Override
	public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
		loadNPCVariablesFromXML(this, null, parentElement, doc, settings);		
	}
	
	@Override
	public void changeFurryLevel(){
	}
	
	@Override
	public boolean isUnique() {
		return true;
	}
	
	@Override
	public DialogueNodeOld getEncounterDialogue() {
		return PixieDialogue.PIXIE_INTRO;		
	}
	
	@Override
	public boolean isRaceConcealed() {
		return concealed;
	}
	
	public void setRaceConceal(boolean conceal)	{
		concealed = conceal;	
	}
}
