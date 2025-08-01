import argparse
import httpx
import os
import sys
from dotenv import load_dotenv

# webhook_manager.py is a thin CLI client —
# a convenience wrapper to register, list, ping, and unregister against the exposee API
# from the shell instead of hand-crafting curl/httpx calls each time.
# It parses the console arguments, builds the appropriate HTTP request to the Heroku exposee,
# and prints the response.

def parse_args():
    p = argparse.ArgumentParser()
    p.add_argument("action", choices=["register", "unregister", "ping", "list"])
    p.add_argument("--exposee", help="URL of the exposee service", default=os.environ.get("EXPOSEE_URL"))
    p.add_argument("--callback", help="public URL the exposee will call", default=os.environ.get("WEBHOOK_URL"))
    p.add_argument("--eventType", default="Register")
    p.add_argument("--id", type=int, help="webhook id to remove")
    return p.parse_args()

def exit_err(msg):
    print(msg, file=sys.stderr)
    sys.exit(1)

def post_parse_sanity_check(args):
    if not args.exposee:
        exit_err("exposee URL required (provide --exposee or set EXPOSEE_URL)")
    if args.action == "register" and not args.id:
        exit_err("id required for register (provide --id)")
    if args.action == "register" and not args.callback:
        exit_err("callback URL required for register (provide --callback or set WEBHOOK_URL)")
    if args.action == "unregister" and not args.id:
        exit_err("id required for unregister")

def register(base, callback_url, event_type, webhook_id):
    payload = {"id": webhook_id, "url": callback_url, "eventType": event_type}
    r = httpx.post(f"{base}/webhooks", json=payload, timeout=10)
    print("REGISTER:", r.status_code, r.text)

def unregister(base, webhook_id):
    r = httpx.delete(f"{base}/webhooks/{webhook_id}", timeout=10)
    print("UNREGISTER:", r.status_code, r.text)

def ping(base):
    r = httpx.post(f"{base}/ping", timeout=10)
    print("PING:", r.status_code, r.text)

def list_hooks(base):
    r = httpx.get(f"{base}/webhooks", timeout=10)
    print("LIST:", r.status_code, r.text)

if __name__ == "__main__":
    load_dotenv()

    args = parse_args()

    post_parse_sanity_check(args)

    exp = args.exposee.rstrip("/")

    if args.action == "register":
        register(exp, args.callback, args.eventType, args.id)
    elif args.action == "unregister":
        unregister(exp, args.id)
    elif args.action == "ping":
        ping(exp)
    elif args.action == "list":
        list_hooks(exp)
