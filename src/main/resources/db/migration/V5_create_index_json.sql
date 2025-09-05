CREATE INDEX IF NOT EXISTS jsonb_index ON public.vurdering USING gin (json);
