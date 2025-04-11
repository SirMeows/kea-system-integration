from fastapi import FastAPI, Form, File, UploadFile
import aiofiles
from typing import Optional
from datetime import datetime

app = FastAPI()

@app.post("/form")
def basic_form(username: str = Form(...), password: str = Form(default=..., min_length=8)):
    print(username, password)
    return { "username": username }

@app.post("/fileform")
async def file_form(file: UploadFile = File(...), description: Optional[str] = None):
    safe_filename = file.filename.replace("/", "_").replace("\\", "_")
    unique_filename = str(datetime.now()) + "__" + safe_filename


    async with aiofiles.open("./uploads/"+unique_filename, "wb") as f:
        # := is the walrus operator
        while content := await file.read(1024): # read in chunks of 1024
            await f.write(content)