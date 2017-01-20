package net.swing.src.ent.mind;

public enum EnumAIcommand
{
	
	STAND, HIDE, // goes to random 'safe' location
	ESCAPE, // runs to random 'safe' location
	RUN, // WPS location
	GOTO, // WPS location
	ATTACK, // nearest/from_line
	REATTACK,
	GET_NEAREST,
	GET_FROM_LINE, // [line]
	BUILD, // at M1 location {blocks}
	SUICIDE,
	ACTIVATE_AREA; // at M2 location
}
