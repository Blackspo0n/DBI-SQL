
/**
 * Erstellen des Objekts Agent, welches bei der Übergabe vom Daten hilft.
 * 
 * @author Markus Hausmann
 * @author Mario Kellner
 * @author Jonas Stadtler
 *
 */
public class Agent
{
	double umsatz;
	String agent;
	
	Agent(double umsatz, String agent)
	{
		this.agent=agent;
		this.umsatz=umsatz;
	}

	public double getUmsatz() {
		return umsatz;
	}

	public void setUmsatz(double umsatz) {
		this.umsatz = umsatz;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}
}
