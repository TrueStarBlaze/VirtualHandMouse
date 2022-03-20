/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.virtualhandmouseprototype;
import java.util.LinkedList;
import java.util.Iterator;
/**
 *
 * @author erick
 */
public class NLinkedList<T> extends LinkedList<T>{
    /*
    tested and approved although be careful of return value from popping
    */
        public void memCheck(int max) {
        while (this.size() > max) {
            this.pop();
        }
    }   
        /*
        only usable for coords
        tested and approved
        */
        public int[][] lastAmt(int amt) {
            int[][] ret = new int[amt][2];
            int indx = 0;
            int lastIndx = this.size() - 1;
            while(indx < amt){
                ret[ret.length - 1 - indx] = 
                        (int[]) this.get(lastIndx - indx);
                indx++;
            }
            return ret;
        }
        public int[][] lastAmtChecked (int amt, int max) {
         this.memCheck(max);
         return this.lastAmt(amt);
         
        }
}
