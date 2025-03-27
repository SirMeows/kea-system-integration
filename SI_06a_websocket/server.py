import asyncio
from websockets.asyncio.server import serve


async def handle_new_websocket(websocket):   
    async for message in websocket:
        print(message)
        await websocket.send(message)


async def main():
    print("Starting websocket server...")
    # Standard port: 8765

    async with serve(handle_new_websocket, "localhost", 8765) as server:
        await server.serve_forever()

asyncio.run(main())
