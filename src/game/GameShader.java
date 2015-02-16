package game;

import org.lwjgl.util.vector.Matrix4f;

import core.Camera;
import core.Shader;
import core.management.Maths;

public class GameShader extends Shader{

	private static String vertexShader = "vertex.glsl";
	private static String fragmentShader = "fragment.glsl";
	
	private int projectionMatrixLocation, viewMatrixLocation, modelMatrixLocation;

	public GameShader() {
		super(vertexShader, fragmentShader);
		
		bindAttribLocation(0, "in_Position");
		bindAttribLocation(1, "in_texCoords");
		linkAndValidate();
		
		projectionMatrixLocation = getUniformLocation("projectionMatrix");
		viewMatrixLocation = getUniformLocation("viewMatrix");
		modelMatrixLocation = getUniformLocation("modelMatrix");
	}

	public int getProjectionMatrixLocation() {
		return projectionMatrixLocation;
	}

	public int getViewMatrixLocation() {
		return viewMatrixLocation;
	}

	public int getModelMatrixLocation() {
		return modelMatrixLocation;
	}

	public void loadViewMatrix(Camera cam) {
		Matrix4f viewMatrix = Maths.createViewMatrix(cam);
		loadMatrix(viewMatrixLocation, viewMatrix);
	}
}
