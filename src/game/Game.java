package game;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import core.Camera;
import core.Time;
import core.Window;
import core.render.MeshObject;
import core.render.TexturedMesh;
import core.render.temp.TempOBJLoader;

public class Game {

	private GameShader shader;
	private Camera cam;

	private TexturedMesh mesh2, meshOBJ;
	private Random r = new Random();
	private ArrayList<MeshObject> list = new ArrayList<MeshObject>();
	private ArrayList<MeshObject> list2 = new ArrayList<MeshObject>();
	private MeshObject object, object2, object3;

	private float red, green, blue;
	
	private Thread genColorThread;
	private boolean genColorThreadIsAlive = false;

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

		// loads 'new TexturedMesh' from a file instead
		meshOBJ = TempOBJLoader.load("C:/Users/kie/Documents/GitHub/LWJGL_Game/Game/bin/stall.obj", "C:/Users/kie/Documents/GitHub/LWJGL_Game/Game/bin/stall.png", shader.getProjectionMatrixLocation(), shader, cam);
		mesh2 = new TexturedMesh(shader.getProjectionMatrixLocation(), shader, "image0.png", cam);
		mesh2.add(getVectorVertices(), getVectorTexCoords(), getIndices());
		object = new MeshObject(new Vector3f(0, 0, -2), 0, 0, 0, 1);
		object2 = new MeshObject(new Vector3f(2, 0, -20), 0, 0, 0, 1);
		object3 = new MeshObject(new Vector3f(5, 7, -20), 0, 0, 0, 1);
		list2.add(object);
		list2.add(object2);
		list.add(object3);

		int objecttotal = 1;
		double percentage = 0;

		red = 0;
		green = 0;
		blue = 0;

		// for (int I = 0; I < objecttotal; I++) {
		// percentage = getPercentage(I, objecttotal);
		// System.out.println(percentage + "%");
		// int x = r.nextInt(400) -150;
		// int y = -(r.nextInt(450)) + 200;
		// int z = -(r.nextInt(450)) + 50;
		// MeshObject mo = new MeshObject(new Vector3f(x, y, z), 0, 0, 0, 1);
		// float xrot = r.nextFloat();
		// float yrot = (r.nextFloat());
		// float zrot = (r.nextFloat());
		// mo.setRotation(xrot, yrot, zrot);
		// list.add(mo);
		// }
	}

	private void start() {
		test();
		while (!Window.isCloseRequested()) {
			// cam.move();
			Window.clearAll(red, green, blue, 0);
			update();
			try {
				render();
			} catch (Exception e) {
			}
		}

		Window.close();
	}

	public void generateClearAllColors() {
		if(genColorThreadIsAlive)
		{
			System.out.println("Already genrating");
			return;
		}
		genColorThread = new Thread(new Runnable() {
			public void run() {
				genColorThreadIsAlive = true;
				float lastR = red;
				float lastG = green;
				float lastB = blue;

				float cR = Float.valueOf(r.nextInt(255)) / 255;
				float cG = Float.valueOf(r.nextInt(255)) / 255;
				float cB = Float.valueOf(r.nextInt(255)) / 255;

				if (lastR < cR) {
					while (true) {
						if (red >= cR) {
							break;
						}
						red += 0.01f;
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} else

				if (lastR > cR) {
					while (true) {
						if (red <= cR) {
							break;
						}
						red -= 0.01f;
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				if (lastG < cG) {
					while (true) {
						if (green >= cG) {
							break;
						}
						green += 0.01f;
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} else

				if (lastG > cG) {
					while (true) {
						if (green <= cG) {
							break;
						}
						green -= 0.01f;
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				if (lastB < cB) {
					while (true) {
						if (blue >= cB) {
							break;
						}
						blue += 0.01f;
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} else

				if (lastB > cB) {
					while (true) {
						if (blue <= cB) {
							break;
						}
						blue -= 0.01f;
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				
				genColorThreadIsAlive = false;
			}
		});
		genColorThread.start();
	}

	private void test() {
		new Thread(new Runnable() {
			public void run() {
				int i = 0;
				while (i < 1000) {
					try {
						i++;
						int x = r.nextInt(1900) - 150;
						int y = -(r.nextInt(1950)) + 200;
						int z = -(r.nextInt(1950)) + 50;
						MeshObject mo = new MeshObject(new Vector3f(x, y, z), 0, 0, 0, 1);
						float xrot = r.nextFloat();
						float yrot = (r.nextFloat());
						float zrot = (r.nextFloat());
						mo.setRotation(xrot, yrot, zrot);
						list.add(mo);
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}).start();
	}

	private void render() {
		shader.bind();
		shader.loadViewMatrix(cam);
		{
			mesh2.draw(shader.getModelMatrixLocation(), list2);
			// instead of making a loop it takes 'list' as an argument
			meshOBJ.draw(shader.getModelMatrixLocation(), list);
			list.get(0).rotate(0.1f, 0.1f, 0.1f);
		}
		shader.unbind();
		Window.update();
		// Window.sync(60);
	}

	private void update() {
		// TODO added input updates
		Input.update(Time.getDelta(), this);
	}

	public Camera getCamera() {
		return cam;
	}

	private double getPercentage(double current, double max) {
		double percentage = (current * 100) / max;
		return percentage;
	}

	private int[] getIndices() {
		int[] indices = new int[] {
				//
				2, 1, 0, //
				0, 3, 2 //
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
