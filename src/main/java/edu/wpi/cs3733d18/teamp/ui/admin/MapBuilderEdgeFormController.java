package edu.wpi.cs3733d18.teamp.ui.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733d18.teamp.Database.DBSystem;

import edu.wpi.cs3733d18.teamp.Exceptions.EdgeNotFoundException;
import edu.wpi.cs3733d18.teamp.Exceptions.NodeNotFoundException;

import edu.wpi.cs3733d18.teamp.Exceptions.OrphanNodeException;
import edu.wpi.cs3733d18.teamp.Pathfinding.Edge;
import edu.wpi.cs3733d18.teamp.Pathfinding.Node;
import edu.wpi.cs3733d18.teamp.ui.admin.MapBuilderController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class MapBuilderEdgeFormController {
    DBSystem db = DBSystem.getInstance();

    Boolean editFlag = false;
    String editedEdgeID = null;
    Edge submittedEdge;


    @FXML
    JFXButton cancelFormButton;

    @FXML
    JFXButton deleteButton;

    @FXML
    JFXButton submitFormButton;

    @FXML
    JFXTextField startNodeTxt;

    @FXML
    JFXTextField endNodeTxt;

    @FXML
    JFXCheckBox isActiveEdgeCheckBox;

    @FXML
    Label edgeFormErrorLabel;

    @FXML
    GridPane formPane;

    /**
     * Initializes the MapBuilderController object and sets the delete button to invisible
     */
    MapBuilderController mapBuilderController;
    public void startUp(MapBuilderController mapBuilderController){
        this.mapBuilderController = mapBuilderController;
        deleteButton.setVisible(false);
    }

    public void startUp(MapBuilderController mapBuilderController, String edgeID, Node startNode,
                        Node endNode, Boolean isActive){
        this.mapBuilderController = mapBuilderController;
        editedEdgeID = edgeID;
        startNodeTxt.setText(startNode.getID());
        endNodeTxt.setText(endNode.getID());
        isActiveEdgeCheckBox.setSelected(isActive);
        deleteButton.setVisible(true);
        editFlag = true;
    }

    /**
     * Cancels the form if the user decides they don't need to make a new node or edge
     * @param e the action of clicking the button
     */
    @FXML
    public void cancelFormButtonOp(ActionEvent e){
        mapBuilderController.addOverlay();
    }

    /**
     * Submits the Edge form, creating a node and then passing it to the EdgeRepo
     * @param e
     */
    @FXML
    public void submitEdgeFormButtonOp(ActionEvent e) {
        Boolean isActive = isActiveEdgeCheckBox.isSelected();
        Node startNode, endNode;
        try {
            startNode = db.getOneNode(startNodeTxt.getText());
            endNode = db.getOneNode(endNodeTxt.getText());
        } catch (NodeNotFoundException nnfe) {
            nnfe.printStackTrace();
            edgeFormErrorLabel.setText("Incompatible input for fields which require numbers.");
            edgeFormErrorLabel.setVisible(true);
            return;
        }
        Edge edge = new Edge(startNode, endNode, isActive);

        if(editFlag){
            edge.setID(editedEdgeID);
            try {
                db.modifyEdge(edge);
            }
            catch(NodeNotFoundException nnfe){
                nnfe.printStackTrace();
            }
            catch(EdgeNotFoundException enfe){
                enfe.printStackTrace();
            }
            editedEdgeID = null;
        } else {
            try {
                db.createEdge(edge);
            }
            catch(NodeNotFoundException nnfe){
                nnfe.printStackTrace();
            }
            catch(EdgeNotFoundException enfe){
                enfe.printStackTrace();
            }
        }

        mapBuilderController.updateMap();
        mapBuilderController.addOverlay();
    }

    /**
     * Sets the value of the start node label
     * @param startNodeID
     */
    public void setStartNodeTxt(String startNodeID){
        startNodeTxt.setText(startNodeID);
    }

    /**
     * Sets the value of the end node label
     * @param endNodeID
     */
    public void setEndNodeTxt(String endNodeID){
        endNodeTxt.setText(endNodeID);
    }

    /**
     * Checks if the focus is on the start node bar
     * @return
     */
    public Boolean checkStartNodeBar(){
        if (startNodeTxt.isFocused()){
            return true;
        }
        return false;
    }

    /**
     * Checks if the focus is on the end node bar
     * @return
     */
    public Boolean checkEndNodeBar(){
        if (endNodeTxt.isFocused())
            return true;
        return false;
    }

    /**
     * Deletes an edge
     */
    public void deleteButtonOp(){
        try{
            db.willEdgeOrphanANode(editedEdgeID);
        } catch (OrphanNodeException o) {
            //edgeFormErrorLabel.setText("Deleting Edge will orphan Node: " + o.getNodeID());
            //edgeFormErrorLabel.setVisible(true);
            return;
        }
        try {
            db.deleteEdge(editedEdgeID);
        } catch (NodeNotFoundException | EdgeNotFoundException ne) {
            ne.printStackTrace();
        }
        mapBuilderController.updateMap();
        mapBuilderController.addOverlay();
    }

}