package de.whs.dbi.pa7.database;

public interface TpsCreatorInterface {
	void beginTransaktion();
	void endAndCommitTransaction();
	boolean dropSchema();
	boolean createSchema();
	boolean createBranchTupel(int n);
	boolean createAccountTupel(int n);
	boolean createTellerTupel(int n);
	void autoSetup(int n);
	boolean isDebug();
	void setDebug(boolean isDebug);
	public void finishUp();
}