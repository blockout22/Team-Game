package game;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import core.Camera;
import core.Window;
import core.render.MeshObject;
import core.render.OBJLoader;
import core.render.TexturedMesh;

public class Game {

	private GameShader shader;
	private Camera cam;

	private TexturedMesh mesh2, meshOBJ;
	private Random r = new Random();
	private ArrayList<MeshObject> list = new ArrayList<MeshObject>();
	private ArrayList<MeshObject> list2 = new ArrayList<MeshObject>();
	private MeshObject object, object2, object3;
	
	public Game() {
		setup();
		start();
	}

	private void setup() {
		Window.createWindow(800, 600, 3.2);
		Window.enableDepthBuffer();
		Window.enableCullFace(2);

		shader = new GameShader();
		cam = new Camera(70, Window.getAspectRatio(), 0.1f, 10000f);

		//loads 'new TexturedMesh' from a file instead
		meshOBJ = OBJLoader.load("C:/Users/kie/Documents/GitHub/LWJGL_Game/Game/bin/stall.obj", "C:/Users/kie/Documents/GitHub/LWJGL_Game/Game/bin/stall.png", shader.getProjectionMatrixLocation(), shader, cam);
		mesh2 = new TexturedMesh(shader.getProjectionMatrixLocation(), shader, "crate.png", cam);
		mesh2.add(getVectorVertices(), getVectorTexCoords(), getIndices());
		object = new MeshObject(new Vector3f(0, 0, -2), 0, 0, 0, 2);
		object2 = new MeshObject(new Vector3f(2, 0, -20), 0, 0, 0, 1);
		object3 = new MeshObject(new Vector3f(5, 7, -20), 0, 0, 0, 1);
		list2.add(object);
		list2.add(object2);
		list.add(object3);

		int objecttotal = 50000;
		double percentage = 0;

		for (int I = 0; I < objecttotal; I++) {
			percentage = getPercentage(I, objecttotal);
			System.out.println(percentage + "%");
			int x = r.nextInt(400) -150;
			int y = -(r.nextInt(450)) + 200;
			int z = -(r.nextInt(450)) + 50;
			MeshObject mo = new MeshObject(new Vector3f(x, y, z), 0, 0, 0, 1);
			float xrot = r.nextFloat();
			float yrot = (r.nextFloat());
			float zrot = (r.nextFloat());
			mo.setRotation(xrot, yrot, zrot);
			list.add(mo);
		}
	}

	private void start() {
		while (!Window.isCloseRequested()) {
			cam.move();
			Window.clearAll(0, 1, 0, 0);
			render();
			update();
		}

		Window.close();
	}

	private void render() {
		shader.bind();
		shader.loadViewMatrix(cam);
		{
			mesh2.draw(shader.getModelMatrixLocation(), list2);
			//instead of making a loop it takes 'list' as an argument
			meshOBJ.draw(shader.getModelMatrixLocation(), list);
		}
		shader.unbind();
		Window.update();
		// Window.sync(60);
	}

	private void update() {
		//TODO added input updates
	}

	private double getPercentage(double current, double max) {
		double percentage = (current * 100) / max;
		return percentage;
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
