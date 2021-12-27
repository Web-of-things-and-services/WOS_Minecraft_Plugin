package iti5.wos;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Board {
    private int stepX, stepZ;
    private Location location;
    private int rowCount = 6;
    private int columnCount = 7;
    private List<List<CouleurToken>> columnList;

    public Board(Location loc){
        this(loc, 0, -1);
    }

    public Board(Location loc, int stepX, int stepZ){
        this.location = loc;
        this.stepX = stepX;
        this.stepZ = stepZ;
        resetBoard();
    }

    public int getStepX() {
        return stepX;
    }

    public void setStepX(int stepX) {
        this.stepX = stepX;
    }

    public int getStepZ() {
        return stepZ;
    }

    public void setStepZ(int stepZ) {
        this.stepZ = stepZ;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public List<List<CouleurToken>> getColumnList() {
        return columnList;
    }

    public List<CouleurToken> getColumn(int n){
        return this.getColumnList().get(n-1);
    }

    public void setColumnList(List<List<CouleurToken>> columnList) {
        this.columnList = columnList;
    }

    public void resetBoard(){
        this.columnList = new ArrayList<List<CouleurToken>>();
        for (int i = 0; i < this.columnCount; i++){
            this.columnList.add(new LinkedList<CouleurToken>());
        }
        this.columnList.get(5).add(CouleurToken.ROUGE);
        this.columnList.get(5).add(CouleurToken.JAUNE);
        this.columnList.get(5).add(CouleurToken.ROUGE);
    }

    public void addToken(int columnNumber, CouleurToken couleur){
        List<CouleurToken> col = this.getColumn(columnNumber);
        if(col.size() < this.getRowCount()){
            col.add(couleur);
        }
    }

    private Material getCouleurTokenToBlock(CouleurToken color){
        switch(color){
            case JAUNE:
                return Material.GOLD_BLOCK;
            case ROUGE:
                return Material.REDSTONE_BLOCK;
            default:
                return null;
        }
    }

    public void renderAll(){
        Block bottomBlock = this.getLocation().getBlock();
        for(int i = 1; i<=this.getRowCount(); i++){
            List<CouleurToken> col = this.getColumn(i);
            Block blockInCol = bottomBlock;
            for (CouleurToken color : col){
                blockInCol.setType(this.getCouleurTokenToBlock(color));
                blockInCol = blockInCol.getRelative(0,1,0);
            }
            bottomBlock.getRelative(this.getStepX(),0,this.getStepZ());
        }
    }

}
