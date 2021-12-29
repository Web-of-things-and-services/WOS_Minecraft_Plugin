package iti5.wos;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javax.naming.ldap.InitialLdapContext;

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
        return this.getColumnList().get(n);
    }

    public void setColumnList(List<List<CouleurToken>> columnList) {
        this.columnList = columnList;
    }

    public void resetBoard(){
        this.columnList = new ArrayList<List<CouleurToken>>();
        for (int i = 0; i < this.columnCount; i++){
            this.columnList.add(new LinkedList<CouleurToken>());
        }
        // this.columnList.get(5).add(CouleurToken.ROUGE);
        // this.columnList.get(5).add(CouleurToken.JAUNE);
        // this.columnList.get(5).add(CouleurToken.ROUGE);
    }

    public void addToken(int columnNumber, CouleurToken couleur){
        List<CouleurToken> col = this.getColumn(columnNumber);
        if(col.size() < this.getRowCount()){
            col.add(couleur);
            System.out.println("[Puissance 4] ajout couleur " + couleur + " en colonne " + columnNumber);
        }
        renderAll();
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

    // placer une ligne de block
    private Block placeLine(Block initialBlock, int dx, int dy, int dz, int length, Material material){
        Block block = initialBlock;
        for(int i = 0; i < length; i++){
            block.setType(material);
            block = block.getRelative(dx, dy, dz);
        }
        return block;
    }

    // la platforme
    public void renderPlatform(){
        Board b = this;
        new BukkitRunnable() {
            public void run() {
                Block block = b.getLocation().getBlock().getRelative(-b.getStepX(), -2, -b.getStepZ());
                for (int i = 0; i < getColumnCount() + 2; i++) {
                    placeLine(block, -b.getStepZ(), 0, b.getStepX(), 10, Material.QUARTZ_BLOCK);
                    block = block.getRelative(b.getStepX(), 0, b.getStepZ());
                }
            }
        }.runTask(Bukkit.getPluginManager().getPlugin("WOS"));
    }

    // le bord bleu du jeu
    public void renderBorder(){
        Board b = this;
        new BukkitRunnable() {
            public void run() {
                Block block = b.getLocation().getBlock().getRelative(-b.getStepX(),-1,-b.getStepZ());
                // Ligne du bas
                block = placeLine(block, b.getStepX(), 0, b.getStepZ(), b.getColumnCount() + 1, Material.LAPIS_BLOCK);
                // Coté
                block = placeLine(block, 0, 1, 0, b.getRowCount() + 1, Material.LAPIS_BLOCK);
                // Ligne du haut
                block = placeLine(block, -b.getStepX(), 0, -b.getStepZ(), b.getColumnCount() + 1, Material.LAPIS_BLOCK);
                // Coté
                block = placeLine(block, 0, -1, 0, b.getRowCount() + 1, Material.LAPIS_BLOCK);
            }
        }.runTask(Bukkit.getPluginManager().getPlugin("WOS"));
    }

    public void renderStaticElements() {
        renderBorder();
        renderPlatform();
    }

    public void renderAll(){
        System.out.println("[Puissance 4] rendering ...");
        Board b = this;
        System.out.println("b.getLocation() : " + b.getLocation());
        System.out.println("b.getRowCount() : " + b.getRowCount());
        new BukkitRunnable() {
            public void run() {
                Block bottomBlock = b.getLocation().getBlock();
                for(int i = 0; i < b.getColumnCount(); i++){
                    System.out.println("i = " + i);
                    List<CouleurToken> col = b.getColumn(i);
                    Block blockInCol = bottomBlock;
                    for (CouleurToken color : col){
                        System.out.println("on place le bloc " + blockInCol.getLocation() + " en " + color);
                        blockInCol.setType(b.getCouleurTokenToBlock(color));
                        blockInCol = blockInCol.getRelative(0,1,0);
                    }
                    bottomBlock = bottomBlock.getRelative(b.getStepX(),0,b.getStepZ());
                }
            }
        }.runTask(Bukkit.getPluginManager().getPlugin("WOS"));
        
        
        System.out.println("[Puissance 4] rendered");
    }

}
