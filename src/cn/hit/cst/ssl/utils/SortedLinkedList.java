package cn.hit.cst.ssl.utils;

import java.util.ArrayList;

public class SortedLinkedList<T extends Sortable> {
	
	private SLLNode<T> node;
	
	public SortedLinkedList(T element){
		this.node = new SLLNode<T>(element);
	}
	
	public void addNode(SLLNode<T> target){
		SLLNode<T> itrNode = this.node, pNode = null;
		while (itrNode != null) {
			if (itrNode.isMeLarger(target)) {
				pNode = itrNode;
				itrNode = itrNode.getNext();
			}
			else{
				if (pNode == null) {
					target.setNext(itrNode);
					this.node = target;
					return;
				}
				else{
					target.setNext(pNode.getNext());
					pNode.setNext(target);
					return;
				}
			}
		}
		pNode.setNext(target);
	}
	
	public ArrayList<T> iterateListNodeElements(){
		SLLNode<T> tNode = this.node;
		ArrayList<T> elements = new ArrayList<T>();
		while (tNode.hasNext()) {
			elements.add((T)tNode.getElement());
			tNode = tNode.getNext();
		}
		elements.add((T)tNode.getElement());
		return elements;
	}
}
