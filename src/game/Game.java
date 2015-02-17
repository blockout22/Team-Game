package game;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import core.Camera;
import core.Window;
import core.render.MeshObject;
import core.render.TexturedMesh;

public class Game {

	private GameShader shader;
	private Camera cam;

	private TexturedMesh mesh, mesh2;
	private MeshObject object, object2;

	public Game() {
		Window.createWindow(800, 600, 3.2);
		Window.enableDepthBuffer();

		shader = new GameShader();
		cam = new Camera(70, Window.getAspectRatio(), 0.1f, 100f);
		mesh = new TexturedMesh(shader.getProjectionMatrixLocation(), shader, "image0.png", cam);
		mesh.add(getVectorVertices(), getVectorTexCoords(), getIndices());
		object = new MeshObject(mesh, new Vector3f(0, 0, -2), 0, 0, 0, 1);
		
		mesh2 = new TexturedMesh(shader.getProjectionMatrixLocation(), shader, "image0.png", cam);
		mesh2.add(getVectorVertices(), getVectorTexCoords(), getIndices());
		object2 = new MeshObject(mesh2, new Vector3f(2, 0, -20), 0, 0, 0, 1);
		
		while (!Window.isCloseRequested()) {
			cam.move();
			Window.clearAll(1, 1, 1, 1);
			shader.bind();
			shader.loadViewMatrix(cam);
			mesh.draw(shader.getModelMatrixLocation(), object);
			mesh2.draw(shader.getModelMatrixLocation(), object2);
			object2.rotate(new Vector3f(0.0f, 100.1f, 0.1f));
			shader.unbind();
			Window.update();
			Window.sync(50);
		}

		Window.close();
	}

	private int[] getIndices() {
		int[] indices = new int[] {
				//
				0, 1, 2, //
				2, 3, 0 //
		};

		return indices;
	}

	// Vector

	private Vector3f[] getVectorVertices() {
		Vector3f[] vertices = new Vector3f[] {
				//
				new Vector3f(-1f, -1f, -2f), // Left top ID: 0
				new Vector3f(0f, 1f, -2f), // Left bottom ID: 1
				new Vector3f(1f, -1f, -2f), // Right bottom ID: 2
				new Vector3f(0f, -1f, -2f) // Right left ID: 3

		};

		return vertices;
	}

	private Vector2f[] getVectorTexCoords() {
		Vector2f[] texCoords = new Vector2f[] {
				//
				new Vector2f(0, 0), //
				new Vector2f(0, 1), //
				new Vector2f(1, 1), //
				new Vector2f(1, 0) //

		};

		return texCoords;
	}
}
