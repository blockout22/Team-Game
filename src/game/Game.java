package game;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import core.Camera;
import core.Time;
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
	private MeshObject object, object2, object3;
	
	public Game() {
		Window.createWindow(800, 600, 3.2);
		Window.enableDepthBuffer();
		Window.enableCullFace(2);

		shader = new GameShader();
		cam = new Camera(70, Window.getAspectRatio(), 0.1f, 100f);

		mesh2 = new TexturedMesh(shader.getProjectionMatrixLocation(), shader, "image0.png", cam);
		meshOBJ = OBJLoader.load("C:/Users/kie/Documents/GitHub/LWJGL_Game/Game/bin/stall.obj", "C:/Users/kie/Documents/GitHub/LWJGL_Game/Game/bin/stall.png", shader.getProjectionMatrixLocation(), shader, cam);
		object = new MeshObject(mesh2, new Vector3f(0, 0, -2), 0, 0, 0, 1);
		mesh2.add(getVectorVertices(), getVectorTexCoords(), getIndices());
		object2 = new MeshObject(meshOBJ, new Vector3f(2, 0, -20), 0, 0, 0, 2);
		object3 = new MeshObject(mesh2, new Vector3f(5, 7, -20), 0, 0, 0, 1);

		int objecttotal = 10000;
		double percentage = 0;

		for (int I = 0; I < objecttotal; I++) {
			percentage = getPercentage(I, objecttotal);
			System.out.println(percentage + "%");
			int x = r.nextInt(400) -150;
			int y = -(r.nextInt(450)) + 200;
			int z = -(r.nextInt(450)) + 50;
			MeshObject mo = new MeshObject(mesh2, new Vector3f(x, y, z), 0, 0, 0, 1);
			float xrot = r.nextFloat();
			float yrot = (r.nextFloat());
			float zrot = (r.nextFloat());
			mo.setRotation(xrot, yrot, zrot);
			list.add(mo);

		}

		while (!Window.isCloseRequested()) {
			cam.move();
			Window.clearAll(0, 1, 0, 0);
			shader.bind();
			shader.loadViewMatrix(cam);
			{
//				mesh2.draw(shader.getModelMatrixLocation(), object);
//				meshOBJ.draw(shader.getModelMatrixLocation(), object2);
//				mesh2.draw(shader.getModelMatrixLocation(), object3);
//				object2.rotate(new Vector3f(0f, 0f, 0f));
				
				meshOBJ.draw(shader.getModelMatrixLocation(), list);
				
//				for (int I = 0; I < list.size(); I++) {
//					meshOBJ.draw(shader.getModelMatrixLocation(), list.get(I));
//					list.get(I).rotate(new Vector3f(r.nextFloat(), 0f, 0f));
//				}
			}
			shader.unbind();
			Window.update();
			// Window.sync(60);
		}

		Window.close();
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
