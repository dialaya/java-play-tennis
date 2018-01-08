/**
 * 
 */
package com.dialaya.tennis;

/**
 * @author dialaya
 *
 */
public interface ObservableSet {
	void addSetObserver(SetObserver g);
	void removeSetObserver(SetObserver g);
	void notifySetObservers();

}
