import asyncio
import websockets

async def send_message():
    print("Sending message from client to server...")
    uri = "ws://localhost:8000"
    async with websockets.connect(uri) as websocket:
        await websocket.send("Hello SirKittyH! It's a glorious day.")
        print(await websocket.recv())


# asyncio.get_event_loop().run_until_complete(send_message)
# after Python 3.7 you can do the following instead:
asyncio.run(send_message())

