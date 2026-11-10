// ===== Konfiguration =====
const WIDTH = 800;
const HEIGHT = 640;
const CELL_SIZE = 32;
const FRAME_RATE = 20;
const NUMBER_OF_APPLES = 200;
const HIGH_SCORE_KEY = "highScore";

// ===== Canvas Setup =====
const canvas = document.getElementById("gameCanvas");
canvas.width = WIDTH;
canvas.height = HEIGHT;
const ctx = canvas.getContext("2d");

// ===== Spielzustand =====
let gameStarted = false;
let gameOver = false;
let gameWin = false;
let highScore = parseInt(localStorage.getItem(HIGH_SCORE_KEY)) || 0;

let foods = [];
let snake = [];

const Direction = {
    UP: "UP",
    DOWN: "DOWN",
    LEFT: "LEFT",
    RIGHT: "RIGHT"
};

let direction = Direction.RIGHT;
let newDirection = Direction.RIGHT;

// ===== Hilfsfunktionen =====
function randomInt(max) {
    return Math.floor(Math.random() * max);
}

function pointsEqual(a, b) {
    return a.x === b.x && a.y === b.y;
}

function containsPoint(list, point) {
    return list.some(p => pointsEqual(p, point));
}

// ===== Spiel-Logik =====
function resetGameData() {
    snake = [];
    foods = [];

    snake.push({
        x: Math.floor(WIDTH / 2 / CELL_SIZE) * CELL_SIZE,
        y: Math.floor(HEIGHT / 2 / CELL_SIZE) * CELL_SIZE
    });

    for (let i = 0; i < NUMBER_OF_APPLES; i++) {
        generateFood();
    }
}

function generateFood() {
    const totalFields = (WIDTH / CELL_SIZE) * (HEIGHT / CELL_SIZE);
    if (snake.length + foods.length >= totalFields) {
        return;
    }

    let newFood;
    do {
        newFood = {
            x: randomInt(WIDTH / CELL_SIZE) * CELL_SIZE,
            y: randomInt(HEIGHT / CELL_SIZE) * CELL_SIZE
        };
    } while (containsPoint(snake, newFood) || containsPoint(foods, newFood));

    foods.push(newFood);
}

/** Aktualisiert und speichert den Highscore. */
function updateHighScore() {
    const currentScore = snake.length - 1;
    if (currentScore > highScore) {
        highScore = currentScore;
        localStorage.setItem(HIGH_SCORE_KEY, highScore);
    }
}

function checkGameWin() {
    const totalColumns = WIDTH / CELL_SIZE;
    const totalRows = HEIGHT / CELL_SIZE;
    const maxPossibleSnakeSize = totalColumns * totalRows;

    if (snake.length === maxPossibleSnakeSize) {
        gameWin = true;
        updateHighScore();
    }
}

function isCollision() {
    const head = snake[0];
    const invalidWidth = head.x < 0 || head.x >= WIDTH;
    const invalidHeight = head.y < 0 || head.y >= HEIGHT;

    if (invalidWidth || invalidHeight) {
        return true;
    }

    // Prüft, ob der Kopf mit einem anderen Körperteil kollidiert
    // (Äquivalent zum HashSet-Vergleich in Java)
    const uniquePositions = new Set(snake.map(p => `${p.x},${p.y}`));
    return uniquePositions.size !== snake.length;
}

function move() {
    direction = newDirection;
    const head = snake[0];
    let newHead;

    switch (direction) {
        case Direction.UP:
            newHead = { x: head.x, y: head.y - CELL_SIZE };
            break;
        case Direction.DOWN:
            newHead = { x: head.x, y: head.y + CELL_SIZE };
            break;
        case Direction.LEFT:
            newHead = { x: head.x - CELL_SIZE, y: head.y };
            break;
        case Direction.RIGHT:
            newHead = { x: head.x + CELL_SIZE, y: head.y };
            break;
    }

    snake.unshift(newHead);

    if (isCollision()) {
        gameOver = true;
        // Neuen Kopf wieder entfernen
        snake.shift();
        // Highscore prüfen und dauerhaft speichern
        updateHighScore();
        return;
    }

    const foodIndex = foods.findIndex(f => pointsEqual(f, newHead));
    if (foodIndex !== -1) {
        foods.splice(foodIndex, 1);
        // Siegesbedingung direkt nach dem Essen prüfen
        checkGameWin();
        // Nur generieren, wenn wir nicht gerade gewonnen haben
        if (!gameWin) {
            generateFood();
        }
    } else {
        snake.pop();
    }
}

// ===== Zeichnen =====
function drawChessboard() {
    const lightColor = "rgb(0, 37, 64)";
    const darkColor = "rgb(0, 21, 41)";

    for (let y = 0; y < HEIGHT; y += CELL_SIZE) {
        for (let x = 0; x < WIDTH; x += CELL_SIZE) {
            const column = x / CELL_SIZE;
            const row = y / CELL_SIZE;
            ctx.fillStyle = (row + column) % 2 === 0 ? lightColor : darkColor;
            ctx.fillRect(x, y, CELL_SIZE, CELL_SIZE);
        }
    }
}

function drawFoods() {
    for (const food of foods) {
        // Apfel
        ctx.fillStyle = "red";
        ctx.beginPath();
        ctx.ellipse(
            food.x + CELL_SIZE / 2,
            food.y + CELL_SIZE / 2,
            (CELL_SIZE - 4) / 2,
            (CELL_SIZE - 4) / 2,
            0, 0, Math.PI * 2
        );
        ctx.fill();

        // Blatt
        ctx.fillStyle = "green";
        ctx.beginPath();
        ctx.ellipse(
            food.x + CELL_SIZE / 2 + CELL_SIZE / 6,
            food.y + CELL_SIZE / 8,
            CELL_SIZE / 6,
            CELL_SIZE / 8,
            0, 0, Math.PI * 2
        );
        ctx.fill();
    }
}

function drawSnake() {
    const snakeLength = snake.length;
    for (let i = 0; i < snakeLength; i++) {
        const point = snake[i];
        // Berechnet einen Wert zwischen 0.0 (Kopf) und 1.0 (Schwanzende)
        const ratio = snakeLength > 1 ? i / (snakeLength - 1) : 0.0;

        // Startfarbe (Kopf): Reines Grün | Zielfarbe (Schwanz): Türkis
        const r = 0;
        const g = Math.round(255 * (1 - ratio) + 200 * ratio);
        const b = Math.round(255 * ratio);

        ctx.fillStyle = `rgb(${r}, ${g}, ${b})`;
        ctx.fillRect(point.x, point.y, CELL_SIZE, CELL_SIZE);
    }
}

function printMessage(message) {
    ctx.fillStyle = "white";
    ctx.font = "30px sans-serif";
    ctx.textAlign = "center";

    let currentHeight = HEIGHT / 3;
    const lines = message.split("\n");

    for (const line of lines) {
        ctx.fillText(line, WIDTH / 2, currentHeight);
        current
        foods.push(newFood);
    }

    /** Aktualisiert und speichert den Highscore. */
    function updateHighScore() {
        const currentScore = snake.length - 1;
        if (currentScore > highScore) {
            highScore = currentScore;
            localStorage.setItem(HIGH_SCORE_KEY, highScore);
        }
    }

    function checkGameWin() {
        const totalColumns = WIDTH / CELL_SIZE;
        const totalRows = HEIGHT / CELL_SIZE;
        const maxPossibleSnakeSize = totalColumns * totalRows;

        if (snake.length === maxPossibleSnakeSize) {
            gameWin = true;
            updateHighScore();
        }
    }

    function isCollision() {
        const head = snake[0];
        const invalidWidth = head.x < 0 || head.x >= WIDTH;
        const invalidHeight = head.y < 0 || head.y >= HEIGHT;

        if (invalidWidth || invalidHeight) {
            return true;
        }

        // Prüft, ob der Kopf mit einem anderen Körperteil kollidiert
        // (Äquivalent zum HashSet-Vergleich in Java)
        const uniquePositions = new Set(snake.map(p => `${p.x},${p.y}`));
        return uniquePositions.size !== snake.length;
    }

    function move() {
        direction = newDirection;
        const head = snake[0];
        let newHead;

        switch (direction) {
            case Direction.UP:
                newHead = {x: head.x, y: head.y - CELL_SIZE};
                break;
            case Direction.DOWN:
                newHead = {x: head.x, y: head.y + CELL_SIZE};
                break;
            case Direction.LEFT:
                newHead = {x: head.x - CELL_SIZE, y: head.y};
                break;
            case Direction.RIGHT:
                newHead = {x: head.x + CELL_SIZE, y: head.y};
                break;
        }

        snake.unshift(newHead);

        if (isCollision()) {
            gameOver = true;
            // Neuen Kopf wieder entfernen
            snake.shift();
            // Highscore prüfen und dauerhaft speichern
            updateHighScore();
            return;
        }

        const foodIndex = foods.findIndex(f => pointsEqual(f, newHead));
        if (foodIndex !== -1) {
            foods.splice(foodIndex, 1);
            // Siegesbedingung direkt nach dem Essen prüfen
            checkGameWin();
            // Nur generieren, wenn wir nicht gerade gewonnen haben
            if (!gameWin) {
                generateFood();
            }
        } else {
            snake.pop();
        }
    }

// ===== Zeichnen =====
    function drawChessboard() {
        const lightColor = "rgb(0, 37, 64)";
        const darkColor = "rgb(0, 21, 41)";

        for (let y = 0; y < HEIGHT; y += CELL_SIZE) {
            for (let x = 0; x < WIDTH; x += CELL_SIZE) {
                const column = x / CELL_SIZE;
                const row = y / CELL_SIZE;
                ctx.fillStyle = (row + column) % 2 === 0 ? lightColor : darkColor;
                ctx.fillRect(x, y, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    function drawFoods() {
        for (const food of foods) {
            // Apfel
            ctx.fillStyle = "red";
            ctx.beginPath();
            ctx.ellipse(
                food.x + CELL_SIZE / 2,
                food.y + CELL_SIZE / 2,
                (CELL_SIZE - 4) / 2,
                (CELL_SIZE - 4) / 2,
                0, 0, Math.PI * 2
            );
            ctx.fill();

            // Blatt
            ctx.fillStyle = "green";
            ctx.beginPath();
            ctx.ellipse(
                food.x + CELL_SIZE / 2 + CELL_SIZE / 6,
                food.y + CELL_SIZE / 8,
                CELL_SIZE / 6,
                CELL_SIZE / 8,
                0, 0, Math.PI * 2
            );
            ctx.fill();
        }
    }

    function drawSnake() {
        const snakeLength = snake.length;
        for (let i = 0; i < snakeLength; i++) {
            const point = snake[i];
            // Berechnet einen Wert zwischen 0.0 (Kopf) und 1.0 (Schwanzende)
            const ratio = snakeLength > 1 ? i / (snakeLength - 1) : 0.0;

            // Startfarbe (Kopf): Reines Grün | Zielfarbe (Schwanz): Türkis
            const r = 0;
            const g = Math.round(255 * (1 - ratio) + 200 * ratio);
            const b = Math.round(255 * ratio);

            ctx.fillStyle = `rgb(${r}, ${g}, ${b})`;
            ctx.fillRect(point.x, point.y, CELL_SIZE, CELL_SIZE);
        }
    }

    function printMessage(message) {
        ctx.fillStyle = "white";
        ctx.font = "30px sans-serif";
        ctx.textAlign = "center";

        let currentHeight = HEIGHT / 3;
        const lines = message.split("\n");

        for (const line of lines) {
            ctx.fillText(line, WIDTH / 2, currentHeight);
            currentHeight += 36; // entspricht ungefähr der FontMetrics-Höhe
        }
    }

    function draw() {
        ctx.clearRect(0, 0, WIDTH, HEIGHT);
        drawChessboard();

        if (!gameStarted) {
            printMessage("Press Space Bar to Begin Game");
            return;
        }

        drawFoods();
        drawSnake();

        if (gameOver) {
            const currentScore = snake.length - 1;
            printMessage(
                "Your Score: " + currentScore +
                "\nHigh Score: " + highScore +
                "\nPress Space Bar to Reset"
            );
        }

        if (gameWin) {
            const currentScore = snake.length - 1;
            printMessage(
                "CONGRATULATIONS!\nYOU WIN!\nFinal Score: " + currentScore +
                "\nPress Space Bar to Restart"
            );
        }
    }

// ===== Game Loop =====
    function gameLoop() {
        if (gameStarted && !gameOver && !gameWin) {
            move();
        }
        draw();
    }

// ===== Tastatursteuerung =====
    function handleKeyEvent(keyCode) {
        if (!gameStarted) {
            if (keyCode === "Space") {
                gameStarted = true;
            }
        } else if (!gameOver && !gameWin) {
            switch (keyCode) {
                case "ArrowUp":
                case "KeyW":
                    if (direction !== Direction.DOWN) {
                        newDirection = Direction.UP;
                    }
                    break;
                case "ArrowDown":
                case "KeyS":
                    if (direction !== Direction.UP) {
                        newDirection = Direction.DOWN;
                    }
                    break;
                case "ArrowRight":
                case "KeyD":
                    if (direction !== Direction.LEFT) {
                        newDirection = Direction.RIGHT;
                    }
                    break;
                case "ArrowLeft":
                case "KeyA":
                    if (direction !== Direction.RIGHT) {
                        newDirection = Direction.LEFT;
                    }
                    break;
            }
        } else if (keyCode === "Space") {
            gameStarted = false;
            gameOver = false;
            gameWin = false;
            direction = Direction.RIGHT;
            newDirection = Direction.RIGHT;
            resetGameData();
        }
    }

    document.addEventListener("keydown", (e) => {
        handleKeyEvent(e.code);
        // Verhindert, dass die Seite bei Pfeiltasten/Leertaste scrollt
        if (["ArrowUp", "ArrowDown", "ArrowLeft", "ArrowRight", "Space"].includes(e.code)) {
            e.preventDefault();
        }
    });

// ===== Spiel starten =====
    function startGame() {
        resetGameData();
        setInterval(gameLoop, 1500 / FRAME_RATE);
    }

    startGame();
}
