import dotenv from "dotenv";
import express from "express";
import taskRoutes from "./routes/tasks.js";
import authRoutes from "./routes/auth.js";

dotenv.config();
const app = express();

app.use(express.json());
app.use("/api/tasks", taskRoutes);
app.use("/api/auth", authRoutes);

app.get("/", (req, res) => res.send("API Tasks funcionando ✅"));

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`Servidor corriendo en puerto ${PORT}`));

// URL
// GET -> http://localhost:3000/api/tasks/  --Obtener todas las tareas de la tabla
// GET -> http://localhost:3000/api/tasks/1  --Obtener la tarea con ID 1
// POST -> http://localhost:3000/api/tasks/ --Crear una nueva tarea

// POST -> http://localhost:3000/api/auth/login
// Body -> { "username": "usuario1", "password": "password123" }  --Iniciar sesión y obtener un token JWT