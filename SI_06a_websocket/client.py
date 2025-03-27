import asyncio
import websockets

async def send_message():
    print("Sending message from client to server...")
    uri = "ws://localhost:8765"
    async with websockets.connect(uri) as websocket:
        await websocket.send("Hello SirKittyH! It's a glorious day.")
        print(await websocket.recv())

asyncio.run(send_message())

