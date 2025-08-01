from fastapi import FastAPI, Request
import uvicorn
from collections import deque
import datetime

app = FastAPI()
received = deque(maxlen=100)

@app.post("/webhook-receiver")
async def receive(req: Request):
    payload = await req.json()
    entry = {
        "timestamp": datetime.datetime.now(datetime.UTC).isoformat() + "Z",
        "headers": dict(req.headers),
        "payload": payload,
    }
    received.appendleft(entry)
    print("Webhook received:", entry)
    return {"status": "ok", "received": entry}

@app.get("/events")
def list_events():
    return {"last_events": list(received)}

if __name__ == "__main__":
    uvicorn.run("integrator_receiver:app", host="0.0.0.0", port=8000)
