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
import com.lilithsthrone.game.character.race.Subspecies;
import com.lilithsthrone.game.dialogue.DialogueNodeOld;
import com.lilithsthrone.game.dialogue.npcDialogue.dominion.PixieDialogue;
import com.lilithsthrone.game.inventory.CharacterInventory;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Colour;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Util.Value;
import com.lilithsthrone.world.WorldType;
import com.lilithsthrone.world.places.PlaceType;


/**
 * 
 * @author LightCanadian
 *
 */
public class Pixie extends NPC {	
	
	private boolean concealed = true;
	
	int dom;
	int maxDom = 1000;
	
	//Set max value for dom target
	private Pixie domTarget;
	//Set max value for sub target
	private Pixie subTarget;
	//Set the actual transformation goal
	private Pixie goalTarget;
	
	
	public Pixie() {
		this(false);
	}
	
	public Pixie(boolean isImported){
		super(isImported,new NameTriplet("Pixel", "Pixie", "Pixie"),"A young succubi that you found in an alley after she was abandon by her mother.",
				0, Main.game.getDateNow().getMonth(),Main.game.getDateNow().getDayOfMonth(),1,
				Gender.F_P_V_B_FUTANARI,Subspecies.HUMAN,RaceStage.PARTIAL,
				new CharacterInventory(0),WorldType.DOMINION,PlaceType.DOMINION_BACK_ALLEYS,true);
		}
	
	@Override
	public void setStartingBody(boolean setPersona) {
		this.setPersonality(Util.newHashMapOfValues(
				new Value<>(PersonalityTrait.AGREEABLENESS, PersonalityWeight.HIGH),
				new Value<>(PersonalityTrait.CONSCIENTIOUSNESS, PersonalityWeight.LOW),
				new Value<>(PersonalityTrait.EXTROVERSION, PersonalityWeight.LOW),
				new Value<>(PersonalityTrait.NEUROTICISM, PersonalityWeight.HIGH),
				new Value<>(PersonalityTrait.ADVENTUROUSNESS, PersonalityWeight.AVERAGE)));
		
		if(!setPersona) {			
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
			
			dom=maxDom/2;
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
	
	@Override
	public void hourlyUpdate() {
		updatGoalBody();
	}
	
	public void setRaceConceal(boolean conceal)	{
		concealed = conceal;	
	}
	
	public void initRef() {
		initSubTarget();
		initDomTarget();
		initGoalBody();
	}
	
	private void initSubTarget() {
		//-----Height-----//
		subTarget.setHeight(152);//5"0
		
		//-----BODY SHAPE-----//
		this.setMuscle(Muscle.ZERO_SOFT.getMedianValue());
		this.setBodySize(BodySize.ZERO_SKINNY.getMedianValue());//Give gaunt bodyshape
		
		//-----BREAST-----//
		this.setBreastSize(CupSize.A.getMeasurement());
		
		//-----HORN-----//
		this.setHornLength(1);
		
		//-----Feminity-----//
		this.setFemininity(80);
	}
	
	private void initDomTarget() {
		//-----Height-----//
		subTarget.setHeight(182);//6"0
		
		//-----BODY SHAPE-----//
		this.setMuscle(Muscle.THREE_MUSCULAR.getMedianValue());
		this.setBodySize(BodySize.TWO_AVERAGE.getMedianValue());
		
		//-----BREAST-----//
		this.setBreastSize(CupSize.DD.getMeasurement());
		
		//-----HORN-----//
		this.setHornLength(6);
		
		//-----Feminity-----//
		this.setFemininity(80);
	}
	
	private void initGoalBody() {
		//-----Height-----//
		subTarget.setHeight(164);//5"4
				
		//-----BODY SHAPE-----//
		this.setMuscle(Muscle.ONE_LIGHTLY_MUSCLED.getMedianValue());
		this.setBodySize(BodySize.TWO_AVERAGE.getMedianValue());
		
		//-----BREAST-----//
		this.setBreastSize(CupSize.C.getMeasurement());
		
		//-----HORN-----//
		this.setHornLength(2);
		
		//-----Feminity-----//
		this.setFemininity(80);
	}
	
	public Pixie getSubTarget() {
		return subTarget;
	}
	
	public Pixie getDomTarget() {
		return domTarget;
	}
	
	public void addDom(int value) {
		if ((dom + value) > maxDom) {
			dom = maxDom;
		}else {
			dom += value;
		}
	}
	
	public void addSub(int value) {
		if((dom - value)<0) {
			dom = 0;
		}else {
			dom -= value;
		}		
	}
	
	public int getDomPercent() {
		return dom*100/maxDom;
	}
	
	/**
	 * compare the current body with goal body to see if TF are applicable.
	 * Also remove TF if goal are reach.
	 */
	public void compareGoalBody() {
		
		//-----Height-----//
		if (this.getHeightValue() > goalTarget.getHeightValue()) {
			//reduce height
		} else if (this.getHeightValue() < goalTarget.getHeightValue()){
			//increase height
		} else {
			//if an height effect is apply remove it  
		}
		
		//------Body shape------//
		if (this.getMuscleValue() > goalTarget.getMuscleValue()) {
			//reduce muscle
		} else if (this.getMuscleValue() < goalTarget.getMuscleValue()){
			//increase muscle
		} else {
			//if  a muscle effect is apply, remove it
		}
		
		if (this.getBodySizeValue() > goalTarget.getBodySizeValue()) {
			//reduce bodysize
		} else if (this.getBodySizeValue() < goalTarget.getBodySizeValue()){
			//increase bodysize
		} else {
			//if a body size apply remove it
		}
		
	}
	/**
	 * update goal body based on dom value
	 */
	private void updatGoalBody() {
		//int absolutHeight = subTarget.getHeightValue() + (getDomPercent()*(domTarget.getHeightValue() - subTarget.getHeightValue())/100);
		//if(goalTarget.getHeightValue() > (105*));
	}
}
