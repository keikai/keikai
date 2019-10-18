package io.keikai.model.sys.dependency;

/**
 * Control the conditional formatting reference dependency
 * @author henrichen
 * @since 3.9.0
 */
//ZSS-1151
public interface ConditionalRef extends Ref{
	
	public int getConditionalId();
}
