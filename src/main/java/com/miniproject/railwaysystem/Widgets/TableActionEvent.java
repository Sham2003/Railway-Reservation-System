/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.miniproject.railwaysystem.Widgets;

/**
 *
 * @author Sham
 */
public interface TableActionEvent {
    public void onBook(int row);
    public void onView(int row);
    public void onPrice(int row);

    /**
     *
     * @param row
     */

    public void onSroute(int row);
}
