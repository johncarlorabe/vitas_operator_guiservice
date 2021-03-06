package com.tlc.common;

import java.util.LinkedList;

public class CircularLinkedList<E> extends LinkedList<E> {
	private static final long serialVersionUID = 1L;
	private static int index = 0;
	public synchronized E nextValue(){
		index=index<this.size()?index:0;
		return this.get(index++);
	}
	public E nextNode(){
		index=(index+1) % this.size();
		return this.get(index);
	}
}
