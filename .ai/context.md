# Project Context

## Architecture

@ARCHITECTURE.md

## Decisions

@DECISIONS.md

## Agents

@AGENTS.md

## Roadmap

@ROADMAP.mdt's architecture and conventions.

The definitive architecture is feature-first. Each feature owns application, domain, infrastructure, and shared packages. Domain repository interfaces are implemented by infrastructure persistence adapters.

The authoritative V3 AI specification is `.ai/knowledge-platform.md`. The assistant remains stateless and generation-only, while V3.2 defines the Retrieval contracts needed for future vector search. Ingestion remains external and retrieval implementation remains future work.
